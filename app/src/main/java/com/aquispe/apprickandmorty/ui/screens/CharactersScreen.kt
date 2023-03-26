package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
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
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            items(characters.size) {
                CharacterCard(character = characters[it])
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character
) {
    Card(
        modifier = Modifier
            .clickable { },
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        val context = LocalContext.current

        val imageLoader = ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 16.dp,
                            bottomEnd = 0.dp,
                            topStart = 16.dp,
                            topEnd = 0.dp
                        )
                    )
            )

            DescriptionCharacter(character)
        }
    }
}

@Composable
fun DescriptionCharacter(character: Character) {
    Column(modifier = Modifier) {
        Text(text = character.name)
        Text(text = "${character.status} - ${character.species}")
        Text(text = "Last known location:")
        Text(text = character.origin.name)
    }
}