package com.aquispe.apprickandmorty.ui.screens.characters

import com.aquispe.apprickandmorty.data.repository.PagingRepository
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    private lateinit var viewModel: CharactersViewModel

    private val pagingRepository: PagingRepository = mockk(relaxed = true)

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CharactersViewModel(pagingRepository)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN getCharacters THEN getAllCharacters`() {
        viewModel.getCharacters

        verify {
            pagingRepository.getAllCharacters()
        }
    }
}
