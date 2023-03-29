package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar() {
    CircularProgressIndicator(
        color = Color.White,
        modifier = Modifier.height(64.dp)
    )
}