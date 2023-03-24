package com.aquispe.apprickandmorty.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TitleScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h4,
        modifier = modifier
    )
}
