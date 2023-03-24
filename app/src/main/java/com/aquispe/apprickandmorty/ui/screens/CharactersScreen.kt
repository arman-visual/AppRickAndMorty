package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquispe.apprickandmorty.domain.model.Character

@Composable
fun CharactersScreen(viewModel: CharactersViewModel = hiltViewModel()) {

    val viewState = viewModel.viewState
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        when (viewState) {
            is CharactersViewModel.ViewState.Content -> CharactersContent(viewState.characters)
            is CharactersViewModel.ViewState.Error -> TODO()
            CharactersViewModel.ViewState.Loading -> ProgressBar()
        }
    }
}

@Composable
private fun ProgressBar() {
    CircularProgressIndicator(
        color = Color.White,
        modifier = Modifier.height(64.dp)
    )
}

@Composable
private fun CharactersContent(characters: List<Character>) {
    TitleScreen(title = "Characters")
    LazyHorizontalGrid(rows = GridCells.Fixed(2)){
        items(characters.size) {
            Text(text = characters[it].name)
        }
    }
}