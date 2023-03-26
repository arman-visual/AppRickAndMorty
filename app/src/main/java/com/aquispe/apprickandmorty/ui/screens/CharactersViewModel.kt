package com.aquispe.apprickandmorty.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.domain.model.Info
import com.aquispe.apprickandmorty.usecases.GetCharacters
import com.aquispe.apprickandmorty.usecases.GetCharactersByPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersByPage: GetCharactersByPage
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    var currentPage by mutableStateOf(1)

    init {
        getCharacters()
        //TODO pagina correctamente
        //Crear UI de Item y BottonNav
        //Crear Screen Detalle
        //Añadir Coil para imagenes
        //Añadir Evento Error en el caso de que no se pueda cargar la pagina
        //Crear Readme

    }

    private fun getCharacters() {
        viewModelScope.launch {
            viewState = ViewState.Loading
            getCharactersByPage.invoke(page = 1).fold(
                { failure ->
                    Log.d("DEBUG", "getCharacters: ${failure.message}}")
                    viewState = ViewState.Error(failure)
                },
                { characters ->
                    Log.d("DEBUG", "getCharacters: ${characters.size}}")
                    viewState = ViewState.Content(characters)
                    //Seteamos la primera ves datos de total de paginas?
                })
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val characters: List<Character>) : ViewState()
    }
}