package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aquispe.apprickandmorty.R

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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rick_and_morty_logo),
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(imageVector = icon, contentDescription = "Back")
                }
            }
        },
        contentColor = Color.White,
        modifier = modifier
    )
}
