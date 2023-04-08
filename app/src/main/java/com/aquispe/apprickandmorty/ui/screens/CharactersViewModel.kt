package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.aquispe.apprickandmorty.data.local.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.toDomain
import com.aquispe.apprickandmorty.data.paging.CharacterPagingSource
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import com.aquispe.apprickandmorty.data.remote.mapper.toDomain
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import com.aquispe.apprickandmorty.data.repository.PagingRepository
import com.aquispe.apprickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterDatabase: CharacterDatabase,
    private val characterService: CharacterService,
    private val pagingRepository: PagingRepository
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    private var _characters = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val characters: Flow<PagingData<Character>> = _characters

    val allCharacters = pagingRepository.getCharacters()

    @OptIn(ExperimentalPagingApi::class)
    fun getAllCharactersV2(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                characterDatabase.characterDbModelDao().getAllCharacters()
            },
            remoteMediator = CharacterRemoteMediator(
                characterService,
                characterDatabase,
            )
        ).flow.map { pagingData ->
            pagingData.map { characterDbModel ->
                characterDbModel.toDomain()
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    fun getSearchCharacters(query: String): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    characterService,
                    query
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { characterApiModel ->
                characterApiModel.toDomain()
            }
        }


    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val characters: List<Character>) : ViewState()
    }
}

const val PAGE_SIZE = 20