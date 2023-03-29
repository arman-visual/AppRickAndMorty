package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aquispe.apprickandmorty.R.*
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar
import com.aquispe.apprickandmorty.ui.screens.shared.ProgressBar

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onDetailScreen: (Int) -> Unit
) {
    Scaffold(
        topBar = { CustomTopBar(title = "Characters")},
        modifier = Modifier
    ) {
        ScreenContent(it, viewModel, onDetailScreen)
    }
}

@Composable
fun ScreenContent(
    paddingValues: PaddingValues,
    viewModel: CharactersViewModel,
    onDetailScreen: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        when (val viewState = viewModel.viewState) {
            is CharactersViewModel.ViewState.Content -> CharactersContent(
                viewState.characters,
                onDetailScreen
            )
            is CharactersViewModel.ViewState.Error -> ErrorView(text = stringResource(string.unexpected_error)) {
                viewModel.getCharacters()
            }
            CharactersViewModel.ViewState.Loading -> ProgressBar()
        }
    }
}

@Composable
private fun CharactersContent(characters: List<Character>, onDetailScreen: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            items(characters.size) {
                CharacterCard(character = characters[it], onDetailScreen)
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onDetailScreen: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onDetailScreen(character.id) },
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        val context = LocalContext.current

//        val imageLoader = ImageLoader.Builder(context)
//            .memoryCache {
//                MemoryCache.Builder(context)
//                    .maxSizePercent(0.25)
//                    .build()
//            }
//            .diskCache {
//                DiskCache.Builder()
//                    .directory(context.cacheDir.resolve("image_cache"))
//                    .maxSizePercent(0.02)
//                    .build()
//            }
//            .build()

        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(500)
                    .build(),
                contentDescription = null,
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
        Text(text = character.name, style = MaterialTheme.typography.caption)
        Text(text = character.species)
    }
}