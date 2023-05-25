package com.aquispe.apprickandmorty.ui.screens.detail

import arrow.core.left
import arrow.core.right
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin
import com.aquispe.apprickandmorty.usecases.GetCharacterByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val getCharacterByIdUseCase: GetCharacterByIdUseCase = mockk(relaxed = true)

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: DetailViewModel

    private val mockCharacter = com.aquispe.apprickandmorty.domain.model.Character(
        id = 361,
        name = "Toxic Rick",
        status = "Dead",
        species = "Humanoid",
        location = Location(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/20"
        ),
        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg",
        created = "2018-01-10T18:20:41.703Z",
        gender = "Male",
        origin = Origin(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/20"
        ),
        episode = listOf("https://rickandmortyapi.com/api/episode/27"),
        url = "https://rickandmortyapi.com/api/character/361",
        type = "Humanoid",
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(getCharacterByIdUseCase, testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN onCharacterClicked THEN getCharacterById`() = runTest(testDispatcher) {
        val characterId = 123456
        val initialState = DetailViewModel.ViewState.Loading

        Assert.assertEquals(initialState, viewModel.viewState)

        coEvery { getCharacterByIdUseCase(id = characterId) } returns mockCharacter.right()
        viewModel.getCharacterById(characterId)

        coVerify(exactly = 1) { getCharacterByIdUseCase(id = characterId) }
        Assert.assertEquals(DetailViewModel.ViewState.Content(mockCharacter), viewModel.viewState)
    }

    @Test
    fun `WHEN onCharacterClicked and there is some error THEN get Error State with throwable`() =
        runTest(testDispatcher) {
            val characterId = 123456
            val throwableExpected = Throwable()
            val initialState = DetailViewModel.ViewState.Loading

            Assert.assertEquals(initialState, viewModel.viewState)

            coEvery { getCharacterByIdUseCase(id = characterId) } returns throwableExpected.left()
            viewModel.getCharacterById(characterId)

            coVerify(exactly = 1) { getCharacterByIdUseCase(id = characterId) }
            Assert.assertEquals(
                DetailViewModel.ViewState.Error(throwableExpected),
                viewModel.viewState
            )
        }
}
