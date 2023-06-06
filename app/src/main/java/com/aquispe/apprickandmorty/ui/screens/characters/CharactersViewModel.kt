package com.aquispe.apprickandmorty.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import com.aquispe.apprickandmorty.data.repository.PagingRepository
import com.aquispe.apprickandmorty.domain.model.Character
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    pagingRepository: PagingRepository,
) : ViewModel() {
    val getCharacters: Flow<PagingData<Character>> =
        pagingRepository.getAllCharacters().cachedIn(viewModelScope)
}
