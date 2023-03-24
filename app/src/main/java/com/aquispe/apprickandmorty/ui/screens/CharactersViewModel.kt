package com.aquispe.apprickandmorty.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.usecases.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            viewState = ViewState.Loading
            getCharacters.invoke().fold(
                { failure ->
                    Log.d("DEBUG", "getCharacters: ${failure.message}}")
                    viewState = ViewState.Error(failure)
                },
                { characters ->
                    Log.d("DEBUG", "getCharacters: ${characters.size}}")
                    viewState = ViewState.Content(characters)
                })
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val characters: List<Character>) : ViewState()
    }
}