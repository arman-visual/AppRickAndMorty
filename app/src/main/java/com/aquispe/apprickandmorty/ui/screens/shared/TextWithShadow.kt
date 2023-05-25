package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier,
) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center,
        modifier = modifier
            .offset(
                x = 2.dp,
                y = 2.dp
            )
            .alpha(0.75f)
    )
    Text(
        text = text,
        color = Color.Black,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
