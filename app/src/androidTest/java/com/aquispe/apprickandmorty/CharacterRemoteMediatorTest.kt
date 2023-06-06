package com.aquispe.apprickandmorty

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.aquispe.apprickandmorty.data.local.database.CharacterDatabase
import com.aquispe.apprickandmorty.data.local.database.entities.CharacterDbModel
import com.aquispe.apprickandmorty.data.paging.CharacterRemoteMediator
import com.aquispe.apprickandmorty.data.remote.model.CharacterApiModel
import com.aquispe.apprickandmorty.data.remote.model.LocationApiModel
import com.aquispe.apprickandmorty.data.remote.model.OriginApiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRemoteMediatorTest {

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

    private val mockDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        CharacterDatabase::class.java
    ).fallbackToDestructiveMigration()
        .build()

    private val mockApi = FakeCharactersApi()

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        mockApi.failureMsg = null
        mockApi.clearCharacters()
    }

    @Test
    fun given_moreData_when_refresh_then_MoreDataIsPresent_and_endOfPagination_isFalse() = runTest {
        // Add mock results for the API to return.
        mockApi.addCharacters(listOf(fakeCharacter, fakeCharacter.copy(id = 2)))

        val remoteMediator = CharacterRemoteMediator(
            mockApi,
            mockDb
        )

        val pagingState = PagingState<Int, CharacterDbModel>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun given_LoadSuccess_with_noMoreData_when_refresh_then_endOfPagination_is_true() = runTest {
        // To test endOfPaginationReached, don't set up the mockApi to return post
        // data here.
        val remoteMediator =  CharacterRemoteMediator(
            mockApi,
            mockDb
        )
        val pagingState = PagingState<Int, CharacterDbModel>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun given_failMsg_when_refresh_and_ErrorOccurs_then_LoadReturnsErrorResult() = runTest {
        // Set up failure message to throw exception from the mock API.
        mockApi.failureMsg = "Throw test failure"

        val remoteMediator = CharacterRemoteMediator(
            mockApi,
            mockDb
        )
        val pagingState = PagingState<Int, CharacterDbModel>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}
