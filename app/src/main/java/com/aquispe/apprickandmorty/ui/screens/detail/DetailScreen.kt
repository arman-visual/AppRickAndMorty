package com.aquispe.apprickandmorty.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aquispe.apprickandmorty.R
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CustomError
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar
import com.aquispe.apprickandmorty.ui.screens.shared.ProgressBar
import com.aquispe.apprickandmorty.ui.screens.shared.TextWithShadow

@Composable
fun DetailScreen(
    id: Int,
    onBackClicked: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                backIcon = Icons.Filled.ArrowBack,
                onBackIconClick = onBackClicked
            )
        },
        modifier = Modifier
    ) {
        LaunchedEffect(Unit) {
            viewModel.getCharacterById(id)
        }
        ScreenContent(id, viewModel, it)
    }
}

@Composable
private fun ScreenContent(
    id: Int,
    viewModel: DetailViewModel,
    it: PaddingValues,
) {
    when (val viewState = viewModel.viewState) {
        is DetailViewModel.ViewState.Content -> DetailContent(it, viewState.character)
        is DetailViewModel.ViewState.Error -> CustomError(errorMessage = viewState.throwable.message) {
            viewModel.getCharacterById(id)
        }

        DetailViewModel.ViewState.Loading -> ProgressBar()
    }
}

@Composable
private fun DetailContent(
    paddingValues: PaddingValues,
    viewState: Character,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CharacterHeader(viewState, paddingValues)
        CharacterBody(viewState)
    }
}

@Composable
private fun CharacterHeader(
    viewState: Character,
    paddingValues: PaddingValues,
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 16.dp)
    ) {
        RoundedImage(viewState.image)

        CharacterName(paddingValues, viewState)
    }
}

@Composable
private fun CharacterBody(viewState: Character) {
    SectionWithInformation(
        titleInformation = stringResource(R.string.species_title),
        information = viewState.species,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_adn)
    )

    Spacer(modifier = Modifier.height(8.dp))

    SectionWithInformation(
        titleInformation = stringResource(R.string.status_title),
        information = viewState.status,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_state)
    )

    Spacer(modifier = Modifier.height(8.dp))

    SectionWithInformation(
        titleInformation = stringResource(R.string.gender_title),
        information = viewState.gender,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_gender)
    )

    Spacer(modifier = Modifier.height(8.dp))

    SectionWithInformation(
        titleInformation = stringResource(R.string.first_seen_in_title),
        information = viewState.location.name,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_first_observe)
    )

    Spacer(modifier = Modifier.height(8.dp))

    SectionWithInformation(
        titleInformation = stringResource(R.string.last_know_location_title),
        information = viewState.origin.name,
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_last_observe)
    )
}

@Composable
private fun CharacterName(
    paddingValues: PaddingValues,
    viewState: Character,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {
        TextWithShadow(text = viewState.name, modifier = Modifier)
    }
}

@Composable
private fun SectionWithInformation(
    titleInformation: String,
    information: String,
    imageVector: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = titleInformation,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = information,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun RoundedImage(
    imageUrl: String,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(500)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .height(300.dp)
        )
    }
}
