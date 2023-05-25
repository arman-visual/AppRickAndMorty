package com.aquispe.apprickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aquispe.apprickandmorty.ui.screens.characters.CharactersScreen
import com.aquispe.apprickandmorty.ui.screens.detail.DetailScreen
import com.aquispe.apprickandmorty.ui.screens.search.SearchScreen
import soy.gabimoreno.armandoquispe2.navigation.NavArg
import soy.gabimoreno.armandoquispe2.navigation.Route

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            CharactersScreen(onDetailScreen = {
                navController.navigate(Route.Detail.createRoute(it))
            }, onClickSearch = {
                navController.navigate(Route.Search.baseRoute)
            })
        }
        composable(
            route = Route.Detail.route,
            arguments = Route.Detail.args
        ) { backStackEntry ->
            DetailScreen(id = backStackEntry.findArg(NavArg.CHARACTER_ID), onBackClicked = {
                navController.popBackStack()
            })
        }

        composable(
            route = Route.Search.route
        ) {
            SearchScreen(onDetailScreen = { navController.navigate(Route.Detail.createRoute(it)) },
                         onClickedBack = { navController.popBackStack() }
            )
        }
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArg): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}
