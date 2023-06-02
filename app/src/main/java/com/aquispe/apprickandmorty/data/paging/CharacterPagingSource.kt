package com.aquispe.apprickandmorty.data.paging

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService,
    private val name: String,
    private val status: String,
    private val gender: String,
) : PagingSource<Int, CharacterApiModel>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterApiModel>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterApiModel> {
        val currentPage = params.key ?: INITIAL_PAGE
        return try {

            val response = characterService.getCharactersByFilter(
                page = currentPage,
                name = name,
                status = status,
                gender = gender,
            )

            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

@VisibleForTesting
internal const val INITIAL_PAGE = 1
