package com.aquispe.apprickandmorty.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquispe.apprickandmorty.usecases.GetCharactersByPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersByPage: GetCharactersByPage
) : ViewModel() {

    fun getCharactersByPage(page: Int) {

        viewModelScope.launch {
            getCharactersByPage.invoke(page).fold(
                { failure ->
                    Log.d("DEBUG", "getCharactersByPage: ${failure.message}}")
                },
                { characters ->
                    Log.d("DEBUG", "getCharactersByPage: ${characters.results.size}}")
                })
            Log.d("DEBUG", "getCharactersByPage: ")
        }
    }
}