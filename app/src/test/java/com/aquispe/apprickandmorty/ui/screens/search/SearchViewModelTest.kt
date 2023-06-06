package com.aquispe.apprickandmorty.ui.screens.search

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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private val pagingRepository: PagingRepository = mockk(relaxed = true)

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(pagingRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a searchQuery WHEN updateQuery THEN change the searchStateUi`() {
        val searchQuery = "Rick"

        viewModel.updateQuery("Rick")

        assertEquals(searchQuery, viewModel.searchStateUi.value.searchQuery)
    }

    @Test
    fun `WHEN searchCharacter THEN change the searchStateUi`() {
        val searchStateUi = viewModel.searchStateUi.value
        val name = searchStateUi.searchQuery
        val gender = searchStateUi.genderFilter.lowercase()
        val status = searchStateUi.statusFilter.lowercase()

        viewModel.searchCharacter()

        verify(exactly = 1) {
            pagingRepository.searchCharacters(name, gender, status)
        }
    }

    @Test
    fun `WHEN clear filters THEN change to empty values`() {
        viewModel.cleanFilters()

        val emptyQuery = ""

        assertEquals(emptyQuery, viewModel.searchStateUi.value.statusFilter)
        assertEquals(emptyQuery, viewModel.searchStateUi.value.genderFilter)
    }

}
