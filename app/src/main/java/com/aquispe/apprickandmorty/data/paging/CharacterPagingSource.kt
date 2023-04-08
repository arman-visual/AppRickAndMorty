package com.aquispe.apprickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.service.CharacterService
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val characterService: CharacterService,
    private val query: String
) : PagingSource<Int, CharacterApiModel>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterApiModel>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterApiModel> {
        val currentPage = params.key ?: 1
        return try {

            val response = characterService.getCharactersByFilter(
                page = currentPage,
                name = "",
                status = "",
                type = "",
                gender = "",
                species = ""
            )

            val endOfPaginationReached = response.results.isEmpty()

            if (response.results.isNotEmpty()) {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
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