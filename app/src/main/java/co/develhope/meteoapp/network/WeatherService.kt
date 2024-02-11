package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.dto.DailyDataRemote
import co.develhope.meteoapp.data.dto.WeeklyDataRemote
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

interface WeatherService {
    @GET("/v1/forecast")
    suspend fun getDaily(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("timezone") timezone: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): Response<DailyDataRemote>

    @GET("/v1/forecast")
    suspend fun getWeekly(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String,
    ): Response<WeeklyDataRemote>
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
    .baseUrl("https://api.open-meteo.com")
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(okHttpClient)
    .build()

val service : WeatherService = retrofit.create(WeatherService::class.java)
