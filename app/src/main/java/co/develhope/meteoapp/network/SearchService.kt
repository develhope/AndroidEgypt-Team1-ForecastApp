package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.dto.SearchDataRemote
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.OffsetDateTime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface SearchService {
    @GET("/v1/search")
    suspend fun getSearchCity(
        @Query("name") name: String,
        @Query("count") count: Int,
        @Query("language") language: String
    ): Response<SearchDataRemote>
}
private val gson: Gson = GsonBuilder()
    .registerTypeAdapter(OffsetDateTime::class.java, OffsetDateTimeTypeAdapter())
    .create()

private val loggingInterceptor = HttpLoggingInterceptor()
    .also { it.level = HttpLoggingInterceptor.Level.BODY }

private val okHttpClient = OkHttpClient.Builder()
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .connectTimeout(30, TimeUnit.SECONDS)
    .addInterceptor(TryCatchInterceptor())
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://geocoding-api.open-meteo.com/")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

val service = retrofit.create(SearchService::class.java)
