package com.aquispe.apprickandmorty.data.paging

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult
import com.aquispe.apprickandmorty.FakeCharactersApi
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.model.LocationApiModel
import com.aquispe.apprickandmorty.data.remote.model.OriginApiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterPagingSourceTest {

    private val fakeCharacter = CharacterApiModel(
        id = 361,
        name = "Toxic Rick",
        status = "Dead",
        species = "Humanoid",
        location = LocationApiModel(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/20"
        ),
        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg",
        created = "2018-01-10T18:20:41.703Z",
        gender = "Male",
        origin = OriginApiModel(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/20"
        ),
        episode = listOf("https://rickandmortyapi.com/api/episode/27"),
        url = "https://rickandmortyapi.com/api/character/361",
        type = "Humanoid",
    )

    private val fakeResultCharacters =
        listOf(
            fakeCharacter,
            fakeCharacter.copy(id = 362),
            fakeCharacter.copy(id = 363),
            fakeCharacter.copy(id = 364)
        )

    private val mockApi = FakeCharactersApi().apply {
        addCharacters(fakeResultCharacters)
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val name = "Rick"
        val status = ""
        val gender = ""
        val expectedNextKey = INITIAL_PAGE + 1

        val pagingSource = CharacterPagingSource(mockApi, name, status, gender)

        val expectedPagingSource = LoadResult.Page(
            data = fakeResultCharacters,
            prevKey = null,
            nextKey = expectedNextKey
        )

        val actual = pagingSource.load(
            Refresh(
                key = null,
                loadSize = 4,
                placeholdersEnabled = false
            )
        )

        Assert.assertEquals(expectedPagingSource, actual)
    }

    @Test
    fun `GIVEN failure message THEN get LoadResultError`() = runTest {
        val name = "Rick"
        val status = "dead"
        val gender = "male"

        mockApi.failureMsg = "An exception ocurred"

        val pagingSource = CharacterPagingSource(mockApi, name, status, gender)

        val actual = pagingSource.load(
            Refresh(
                key = null,
                loadSize = 4,
                placeholdersEnabled = false
            )
        )

        Assert.assertTrue(actual is LoadResult.Error)
    }
}
