package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.dto.SearchDataRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/v1/search")
    suspend fun getSearchCity(
        @Query("name") name: String,
        @Query("count") count: Int,
        @Query("language") language: String
    ): Response<SearchDataRemote>
}
