package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aquispe.apprickandmorty.R
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.screens.shared.CustomTopBar
import com.aquispe.apprickandmorty.ui.screens.shared.ProgressBar
import com.aquispe.apprickandmorty.ui.screens.shared.TextWithShadow

//Paginar
//Implementar Test
@Composable
fun DetailScreen(id: Int, viewModel: DetailViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { CustomTopBar(title = "Detail") },
        modifier = Modifier
    ) {
        viewModel.getCharacterById(id)
        ScreenContent(id,viewModel, it)
    }
}

@Composable
private fun ScreenContent(
    id:Int,
    viewModel: DetailViewModel,
    it: PaddingValues
) {
    when (val viewState = viewModel.viewState) {
        is DetailViewModel.ViewState.Content -> DetailContent(it, viewState.character)
        is DetailViewModel.ViewState.Error -> ErrorView(text = stringResource(R.string.unexpected_error)) {
            viewModel.getCharacterById(id)
        }
        DetailViewModel.ViewState.Loading -> ProgressBar()
    }
}

@Composable
private fun DetailContent(
    paddingValues: PaddingValues,
    viewState: Character
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        ImageDetail(viewState.name, viewState.image)

        SectionWithInformation("Species:", viewState.species)

        Spacer(modifier = Modifier.height(8.dp))

        SectionWithInformation("Status:", viewState.status)

        Spacer(modifier = Modifier.height(8.dp))

        SectionWithInformation("Gender:", viewState.gender)

        Spacer(modifier = Modifier.height(8.dp))

        SectionWithInformation("First seen in:", viewState.location.name)

        Spacer(modifier = Modifier.height(8.dp))

        SectionWithInformation("Last know location:", viewState.origin.name)

    }
}

@Composable
private fun SectionWithInformation(
    titleInformation: String,
    information: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = Color(0xff97ce4c),
                shape = RoundedCornerShape(4.dp)
            )
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Image(
            imageVector = Icons.Filled.Info,
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
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ImageDetail(
    name: String,
    imageUrl: String
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(500)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(512.dp)
        )

        TextWithShadow(text = name, modifier = Modifier)
    }
}