package com.aquispe.apprickandmorty.usecases

import arrow.core.left
import arrow.core.right
import com.aquispe.apprickandmorty.domain.model.Location
import com.aquispe.apprickandmorty.domain.model.Origin
import com.aquispe.apprickandmorty.domain.repository.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCharacterByIdUseCaseTest {

    @MockK
    private lateinit var repository: CharacterRepository

    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

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
        MockKAnnotations.init(this)
        getCharacterByIdUseCase = GetCharacterByIdUseCase(repository)
    }

    @Test
    fun `GIVEN success response WHEN getCharacterId THEN get expected result`() = runBlocking {
        val responseExpected = mockCharacter.right()
        val characterId = 361
        coEvery { repository.getCharacterById(characterId) } returns responseExpected

        val response = getCharacterByIdUseCase.invoke(characterId)

        coVerify(exactly = 1) { repository.getCharacterById(characterId) }
        Assert.assertEquals(responseExpected, response)
    }

    @Test
    fun `GIVEN exception response WHEN getCharacterId THEN get expected result`() = runBlocking {
        val exceptionExpected = Throwable().left()
        val characterId = 361
        coEvery { repository.getCharacterById(characterId) } returns exceptionExpected

        val response = getCharacterByIdUseCase.invoke(characterId)

        coVerify(exactly = 1) { repository.getCharacterById(characterId) }
        Assert.assertEquals(exceptionExpected, response)
    }
}
