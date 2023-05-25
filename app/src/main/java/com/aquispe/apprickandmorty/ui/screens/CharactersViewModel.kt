package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aquispe.apprickandmorty.data.repository.CharacterRepository
import com.aquispe.apprickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    val getAllCharacters = characterRepository.getAllCharacters()

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val characters: List<Character>) : ViewState()
    }
}

const val PAGE_SIZE = 20