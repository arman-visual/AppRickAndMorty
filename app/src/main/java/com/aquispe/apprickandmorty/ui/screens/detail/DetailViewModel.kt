package com.aquispe.apprickandmorty.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import com.aquispe.apprickandmorty.di.Main
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.usecases.GetCharacterByIdUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    @Main private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    fun getCharacterById(id: Int) {
        viewModelScope.launch(dispatcher) {
            viewState = ViewState.Loading
            getCharacterByIdUseCase(id).fold(
                { failure ->
                    viewState = ViewState.Error(failure)
                },
                { character ->
                    viewState = ViewState.Content(character)
                }
            )
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Error(val throwable: Throwable) : ViewState()
        data class Content(val character: Character) : ViewState()
    }
}
