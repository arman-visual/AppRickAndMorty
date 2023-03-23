package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = hiltViewModel()) {
    viewModel.getCharactersByPage(1)
}
