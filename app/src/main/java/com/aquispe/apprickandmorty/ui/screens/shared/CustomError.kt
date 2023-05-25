package com.aquispe.apprickandmorty.ui.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aquispe.apprickandmorty.R

@Composable
fun CustomError(
    text: String = "Error",
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.rick_and_morty_error),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Text(
            text,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
        TextButton(onClick = onClick) {
            Text("Try again")
        }
    }
}