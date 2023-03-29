package com.aquispe.apprickandmorty.ui.navigation

sealed class Route(
    val baseRoute: String
) {
    object Home : Route("home")

    object Detail : Route("detail/{id}") {
        fun createRoute(characterId: Int) = "detail/$characterId"
    }
}
