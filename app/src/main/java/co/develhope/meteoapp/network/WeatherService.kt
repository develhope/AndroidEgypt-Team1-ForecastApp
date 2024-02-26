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
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
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

