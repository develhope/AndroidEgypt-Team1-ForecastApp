package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.data.dto.toDailyDataLocal
import co.develhope.meteoapp.data.dto.toWeeklyDataLocal
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.OffsetDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepo {

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

    private val service = retrofit.create(WeatherService::class.java)

    private val dailyData =
        "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,cloudcover,windspeed_10m,winddirection_10m,uv_index,is_day"

    private val weeklyData =
        "precipitation_sum,temperature_2m_max,temperature_2m_min,weathercode,windspeed_10m_max"

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String
    ): DailyDataLocal? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDateFormatted = dateFormat.format(dateFormat.parse(startDate))
        val endDateFormatted = dateFormat.format(dateFormat.parse(endDate))

        val response = service.getDaily(
            lat,
            lon,
            dailyData,
            "UTC",
            startDateFormatted,
            endDateFormatted
        )

        return response.toDailyDataLocal()
    }

    suspend fun getHomeWeather(
        lat: Double,
        lon: Double,
    ): WeeklyDataLocal? {
        val response = service.getWeekly(lat, lon, weeklyData, "UTC")

        return response.toWeeklyDataLocal()
    }
}
