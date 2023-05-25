package com.aquispe.apprickandmorty.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin
import com.aquispe.apprickandmorty.ui.screens.characters.CharactersContent
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeCharacters = (1..50).map {
        com.aquispe.apprickandmorty.domain.model.Character(
            id = it,
            name = "Name $it",
            status = "Status $it",
            species = "Species $it",
            origin = Origin(
                name = "Origin $it",
                url = "Url $it"
            ),
            type = "",
            url = "Url $it",
            gender = "",
            episode = emptyList(),
            created = "",
            image = "",
            location = Location(
                name = "Location $it",
                url = "Url $it"
            )
        )
    }

    @Before
    fun setup() {
        composeTestRule.setContent {
            val fakePagingData = flowOf(PagingData.from(fakeCharacters)).collectAsLazyPagingItems()
            CharactersContent(
                pagingItems = fakePagingData,
                onDetailScreen = {}
            )
        }
    }

    @Test
    fun whenScrollTo10PositionCheckInfoItem() {
        composeTestRule.onNode(hasScrollAction()).performScrollToIndex(10)
        Thread.sleep(3000)
        composeTestRule.onNodeWithText("Name 12").assertIsDisplayed()
    }
}
