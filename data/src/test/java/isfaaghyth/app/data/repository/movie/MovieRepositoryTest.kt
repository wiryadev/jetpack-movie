package isfaaghyth.app.data.repository.movie

import isfaaghyth.app.data.entity.Movie
import isfaaghyth.app.data.entity.Movies
import isfaaghyth.app.data.routes.NetworkServices
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

class MovieRepositoryTest {

    private var services = mock(NetworkServices::class.java)
    private lateinit var repository: MovieRepository

    private val movies = listOf(
        Movie(
            "id",
            "movieId",
            "title",
            "posterPath",
            "overview",
            "backdrop",
            0,
            0f,
            "relateDate"
        )
    )

    @Before fun setUp() {
        repository = MovieRepositoryImpl(services)
    }

    @Test fun `should get popular movie success`() = runBlocking {
        `when`(services.getPopularMovie()).thenReturn(
            Response.success(Movies(movies))
        )
        val repo = repository.getPopularMovie()

        assertEquals(repo.body(), Movies(movies))
    }

    @Test fun `should get null and error`() = runBlocking {
        `when`(services.getPopularMovie()).thenReturn(
            Response.error(401, "".toResponseBody("application/json".toMediaTypeOrNull()))
        )
        val repo = repository.getPopularMovie()

        assertEquals(repo.body(), null)
    }

}