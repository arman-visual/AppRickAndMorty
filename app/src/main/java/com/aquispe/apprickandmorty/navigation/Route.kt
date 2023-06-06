package com.aquispe.apprickandmorty.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList(),
) {
    object Home : Route("home")
    object Detail : Route("detail", navArgs = listOf(NavArg.CHARACTER_ID)) {
        fun createRoute(characterId: Int) = "${baseRoute}/$characterId"
    }

    object Search : Route("search")

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}

enum class NavArg(
    val key: String,
    val navType: NavType<*>,
) {
    CHARACTER_ID("id", NavType.IntType)
}
