package com.aquispe.apprickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aquispe.apprickandmorty.ui.screens.CharactersScreen
import com.aquispe.apprickandmorty.ui.screens.DetailScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Home.baseRoute) {
        composable(Route.Home.baseRoute) {
            CharactersScreen(onDetailScreen = {
                navController.navigate(Route.Detail.createRoute(it))
            })
        }
        composable(
            route = Route.Detail.baseRoute,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailScreen(id = backStackEntry.arguments?.getInt("id") ?: 0)
        }
    }
}
