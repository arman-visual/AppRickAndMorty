package com.aquispe.apprickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.aquispe.apprickandmorty.ui.navigation.Navigation
import com.aquispe.apprickandmorty.ui.screens.CharactersScreen
import com.aquispe.apprickandmorty.ui.screens.DetailScreen
import com.aquispe.apprickandmorty.ui.screens.shared.CustomError
import com.aquispe.apprickandmorty.ui.theme.AppRickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}