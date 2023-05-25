package com.aquispe.apprickandmorty.ui.screens.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aquispe.apprickandmorty.R
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CharacterCard
import com.aquispe.apprickandmorty.ui.screens.shared.CustomError
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar
import com.aquispe.apprickandmorty.ui.screens.shared.ProgressBar

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onDetailScreen: (Int) -> Unit,
    onClickSearch: () -> Unit = {},
    onBackNavigation: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                searchIcon = Icons.Filled.Search,
                onSearchIconClick = onClickSearch,
                onBackIconClick = onBackNavigation
            )
        },
        modifier = Modifier
    ) {
        ScreenContent(it, viewModel, onDetailScreen)
    }
}

@Composable
fun ScreenContent(
    paddingValues: PaddingValues,
    viewModel: CharactersViewModel,
    onDetailScreen: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        val charactersPaginated = viewModel.getCharacters.collectAsLazyPagingItems()
        val loadState = charactersPaginated.loadState.mediator

        PaginateLoadState(loadState, charactersPaginated)

        CharactersContent(charactersPaginated, onDetailScreen)
    }
}

@Composable
private fun PaginateLoadState(
    loadState: LoadStates?,
    charactersPaginated: LazyPagingItems<Character>,
) {
    if (loadState?.refresh is LoadState.Loading) {
        LoadingState(
            modifier = Modifier.fillMaxSize(),
            loadingText = stringResource(R.string.paging_refresh_state_loading)
        )
    } else if (loadState?.refresh is LoadState.Error) {
        (loadState.refresh as LoadState.Error).error.message?.let { message ->
            CustomError(
                errorMessage = message,
                onClick = {
                    charactersPaginated.retry()
                })
        }
    } else if (loadState?.append is LoadState.Error) {
        (loadState.append as LoadState.Error).error.message?.let { message ->
            CustomError(
                errorMessage = message,
                onClick = {
                    charactersPaginated.retry()
                })
        }
    }
}

@Composable
fun CharactersContent(
    pagingItems: LazyPagingItems<Character>,
    onDetailScreen: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let {
                    CharacterCard(character = it, onDetailScreen)
                }
            }
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    loadingText: String? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        loadingText?.let {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = it
            )
        }
        ProgressBar()
    }
}
