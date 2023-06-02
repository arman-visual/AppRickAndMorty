package com.aquispe.apprickandmorty.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.aquispe.apprickandmorty.data.repository.PagingRepository
import com.aquispe.apprickandmorty.di.Main
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pagingRepository: PagingRepository,
    @Main private val dispatcher: CoroutineDispatcher,
) :
    ViewModel() {

    private var _searchStateUi = MutableStateFlow(SearchStateUi())
    val searchStateUi = _searchStateUi.asStateFlow()

    fun searchCharacter() {
        viewModelScope.launch (dispatcher){
            val name = _searchStateUi.value.searchQuery
            val gender = _searchStateUi.value.genderFilter.lowercase()
            val status = _searchStateUi.value.statusFilter.lowercase()
            pagingRepository.searchCharacters(name, gender, status).cachedIn(viewModelScope)
                .collect { pagingData ->
                    _searchStateUi.value =
                        _searchStateUi.value.copy(pagingData = MutableStateFlow(pagingData))
                }
        }
    }

    fun updateQuery(query: String) {
        _searchStateUi.value = _searchStateUi.value.copy(searchQuery = query)
    }

    fun cleanQuery() {
        _searchStateUi.value = _searchStateUi.value.copy(searchQuery = "")
    }

    fun updateStatusFilter(statusFilter: String) {
        _searchStateUi.value = _searchStateUi.value.copy(statusFilter = statusFilter)
    }

    fun updateGenderFilter(gender: String) {
        _searchStateUi.value = _searchStateUi.value.copy(genderFilter = gender)
    }

    fun updateExpandedStatus(isExpanded: Boolean) {
        _searchStateUi.value = _searchStateUi.value.copy(
            isExpandedStatus = isExpanded
        )
    }

    fun updateExpandedGender(isExpanded: Boolean) {
        _searchStateUi.value = _searchStateUi.value.copy(
            isExpandedGender = isExpanded
        )
    }

    fun cleanFilters() {
        _searchStateUi.value = _searchStateUi.value.copy(
            statusFilter = "",
            genderFilter = ""
        )
    }
}

data class SearchStateUi(
    val searchQuery: String = "",
    val statusFilter: String = "",
    val genderFilter: String = "",
    val isExpandedStatus: Boolean = false,
    val isExpandedGender: Boolean = false,
    val pagingData: StateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty()),
)
