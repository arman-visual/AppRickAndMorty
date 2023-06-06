package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.aquispe.apprickandmorty.R

@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    backIcon: ImageVector? = null,
    searchIcon: ImageVector? = null,
    onBackIconClick: () -> Unit = {},
    onSearchIconClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rick_and_morty_logo),
                    contentDescription = null,
                    modifier = modifier.weight(1f)
                )
                if (searchIcon != null) {
                    IconButton(onClick = { onSearchIconClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (backIcon != null) {
                IconButton(onClick = { onBackIconClick() }) {
                    Icon(imageVector = backIcon, contentDescription = "Back")
                }
            }
        },
        contentColor = Color.White,
        modifier = modifier
    )
}
