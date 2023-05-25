package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aquispe.apprickandmorty.domain.model.Character
import com.aquispe.apprickandmorty.ui.theme.Black

@Composable
fun CharacterCard(
    character: Character,
    onDetailScreen: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(168.dp)
            .clickable { onDetailScreen(character.id) },
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(character.image)
                        .crossfade(500)
                        .build(),
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 0.dp,
                                topStart = 16.dp,
                                topEnd = 0.dp
                            )
                        )
                )

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    DescriptionCharacter(character)
                }
            }
        }
    }
}

@Composable
fun DescriptionCharacter(character: Character) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Black.copy(alpha = 0.9f))
            .fillMaxWidth()
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}
