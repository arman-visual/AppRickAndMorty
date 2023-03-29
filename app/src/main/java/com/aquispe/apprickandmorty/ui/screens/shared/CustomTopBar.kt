package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomTopBar(
    title: String,
    color: Color = Color.White,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        backgroundColor = Color(0xff97ce4c),
        title = {
            Column {
                Text(text = title, color = color)
            }
        },
        navigationIcon = {
            if(icon != null) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(imageVector = icon, contentDescription = "Back")
                }
            }
        },
        contentColor = Color.White,
        modifier = modifier
    )
}
