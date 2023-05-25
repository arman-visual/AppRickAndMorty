package com.aquispe.apprickandmorty.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.aquispe.apprickandmorty.R
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CharacterCard
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar
import com.aquispe.apprickandmorty.ui.theme.Green
import com.aquispe.apprickandmorty.ui.theme.GreenDark

@Composable
fun SearchScreen(
    onClickedBack: () -> Unit,
    onDetailScreen: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                backIcon = Icons.Filled.ArrowBack,
                onBackIconClick = onClickedBack
            )
        },
        modifier = Modifier
    ) { paddingValues ->
        SearchContent(viewModel, paddingValues, onDetailScreen)
    }
}

@Composable
private fun SearchContent(
    viewModel: SearchViewModel,
    paddingValues: PaddingValues,
    onDetailScreen: (Int) -> Unit,
) {
    val stateUi = viewModel.searchStateUi.collectAsState()
    val query = stateUi.value.searchQuery
    val searchCharacters = stateUi.value.pagingData.collectAsLazyPagingItems()
    Column {
        SearchWidget(query, viewModel, paddingValues)
        if (searchCharacters.loadState.refresh is LoadState.Error) {
            val error = (searchCharacters.loadState.refresh as LoadState.Error).error
            SearchResultError(errorMessage = error)
        } else {
            ResultContent(searchCharacters) {
                onDetailScreen(it)
            }
        }
    }
}

@Composable
private fun SearchWidget(
    query: String,
    viewModel: SearchViewModel,
    paddingValues: PaddingValues,
) {
    var showFilter by remember { mutableStateOf(false) }
    val stateUi by viewModel.searchStateUi.collectAsState()

    OutlinedTextField(
        value = query,
        onValueChange = {
            viewModel.updateQuery(it)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (query.isNotEmpty())
                    viewModel.searchCharacter()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.serach_icon_description),
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { viewModel.cleanQuery() },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon_description),
                    )
                }
            } else {
                IconButton(
                    onClick = { showFilter = true },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                        contentDescription = stringResource(R.string.filter_icon_description),
                        tint = if (stateUi.genderFilter.isNotEmpty() || stateUi.statusFilter.isNotEmpty()) GreenDark else Color.DarkGray,
                        modifier = Modifier
                            .height(32.dp)
                    )
                }
            }
        },
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxWidth()
    )

    if (showFilter)
        FilterDialog(viewModel) {
            showFilter = false
        }
}

@Composable
fun ResultContent(
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
fun SearchResultError(
    errorMessage: Throwable,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rick_and_morty_error),
            contentDescription = stringResource(R.string.error_image_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            text = errorMessage.message ?: stringResource(R.string.default_error),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
fun FilterDialog(
    viewModel: SearchViewModel,
    onDismissRequest: () -> Unit = {},
) {
    val filterStatus = listOf("Alive", "Dead", "Unknown")
    val filterGender = listOf("Female", "Male", "Unknown")

    val stateUi by viewModel.searchStateUi.collectAsState()

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.title_filter_dialog),
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                IconButton(onClick = { onDismissRequest() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.icon_close_description)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.title_filter_status))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = stateUi.statusFilter,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = GreenDark
                    ),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.placeholder_filter_select),
                            fontFamily = FontFamily.SansSerif
                        )
                    },
                    onValueChange = { viewModel.updateStatusFilter(it) },
                    enabled = false,
                    readOnly = true,
                    modifier = Modifier
                        .clickable {
                            viewModel.updateExpandedStatus(true)
                        },
                )
                DropdownMenu(
                    expanded = stateUi.isExpandedStatus,
                    onDismissRequest = { viewModel.updateExpandedStatus(false) },
                    modifier = Modifier
                ) {
                    filterStatus.forEach { filter ->
                        DropdownMenuItem(onClick = {
                            viewModel.updateExpandedStatus(false)
                            viewModel.updateStatusFilter(filter)
                        }) {
                            FilterSelected(filter)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.title_filter_gender))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = stateUi.genderFilter,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = GreenDark
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.placeholder_filter_select),
                            fontFamily = FontFamily.SansSerif,
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    onValueChange = { viewModel.updateGenderFilter(it) },
                    enabled = false,
                    readOnly = true,
                    modifier = Modifier
                        .clickable { viewModel.updateExpandedGender(true) },
                )
                DropdownMenu(
                    expanded = stateUi.isExpandedGender,
                    onDismissRequest = { viewModel.updateExpandedGender(false) },
                    modifier = Modifier
                ) {
                    filterGender.forEach { filter ->
                        DropdownMenuItem(onClick = {
                            viewModel.updateExpandedGender(false)
                            viewModel.updateGenderFilter(filter)
                        }) {
                            FilterSelected(filter)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.action_clean_filter),
                    style = MaterialTheme.typography.button,
                    fontFamily = FontFamily.SansSerif,
                    color = Green,
                    modifier = Modifier.clickable { viewModel.cleanFilters() }
                )
                Button(onClick = {
                    onDismissRequest()
                    viewModel.searchCharacter()
                }) {
                    Text(
                        text = stringResource(R.string.action_search),
                        style = MaterialTheme.typography.button,
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterSelected(filter: String) {
    Text(text = filter, color = Green, fontFamily = FontFamily.SansSerif)
}
