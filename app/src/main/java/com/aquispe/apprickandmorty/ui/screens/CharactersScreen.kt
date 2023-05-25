package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onDetailScreen: (Int) -> Unit
) {
    Scaffold(
        topBar = { CustomTopBar(title = "Characters") },
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

        val characters = viewModel.getAllCharacters.collectAsLazyPagingItems()
        CharactersContent(characters = characters, onDetailScreen = onDetailScreen)
    }
}

@Composable
private fun CharactersContent(
    characters: LazyPagingItems<Character>,
    onDetailScreen: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn{
            items(characters) { character ->
                character?.let {
                    CharacterCard(character = character, onDetailScreen)
                }
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
        modifier = Modifier.padding(vertical = 12.dp)
            .clickable { onDetailScreen(character.id) },
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        val context = LocalContext.current

        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
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