package com.aquispe.apprickandmorty.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.aquispe.apprickandmorty.data.local.database.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterRemoteKeys
import com.aquispe.apprickandmorty.data.remote.mapper.toDbModel
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import retrofit2.HttpException
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val characterService: CharacterService,
    private val characterDatabase: CharacterDatabase,
) : RemoteMediator<Int, CharacterDbModel>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (characterDatabase.remoteKeysDao().getCreationTime()
                ?: 0) < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDbModel>,
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys: CharacterRemoteKeys? = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys: CharacterRemoteKeys? = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse =
                characterService.getCharactersByPage(page = page)

            val characters = apiResponse.results
            val endOfPaginationReached = characters.isEmpty()

            characterDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    clearDatabase()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys: List<CharacterRemoteKeys> = characters.map { characterApiModel ->
                    CharacterRemoteKeys(
                        id = characterApiModel.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                        currentPage = page
                    )
                }

                saveInDataBase(remoteKeys, characters)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun clearDatabase() {
        characterDatabase.remoteKeysDao().clearRemoteKeys()
        characterDatabase.characterDbModelDao().deleteAllCharacters()
    }

    private suspend fun saveInDataBase(
        remoteKeys: List<CharacterRemoteKeys>,
        characters: List<CharacterApiModel>,
    ) {
        characterDatabase.remoteKeysDao().addAllRemoteKeys(remoteKeys)
        characterDatabase.characterDbModelDao()
            .insertCharacters(characters.map { it.toDbModel() })
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterDbModel>): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterDatabase.remoteKeysDao().getRemoteKeyById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterDbModel>): CharacterRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { characterDbModel ->
            characterDatabase.remoteKeysDao().getRemoteKeyById(characterDbModel.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterDbModel>): CharacterRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { characterDbModel ->
            characterDatabase.remoteKeysDao().getRemoteKeyById(characterDbModel.id)
        }
    }
}
