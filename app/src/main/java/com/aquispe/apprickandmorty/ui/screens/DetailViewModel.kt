package com.aquispe.apprickandmorty.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.usecases.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    var currentPage by mutableStateOf(1)

    init {
        getCharacterById(2)
        //Añadir Evento Error en el caso de que no se pueda cargar la pagina
        //Crear Readme
    }

    private fun getCharacterById(id:Int) {
        viewModelScope.launch {
            viewState = ViewState.Loading
            getCharacterByIdUseCase(id).fold(
                { failure ->
                    Log.d("DEBUG", "getCharacters: ${failure.message}}")
                    viewState = ViewState.Error(failure)
                },
                { character ->
                    viewState = ViewState.Content(character)
                })
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val character: Character) : ViewState()
    }
}