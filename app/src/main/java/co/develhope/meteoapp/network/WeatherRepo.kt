package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.data.dto.toDailyDataLocal
import co.develhope.meteoapp.data.dto.toWeeklyDataLocal
import co.develhope.meteoapp.data.utills.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val service: WeatherService) {

    private val dailyData =
        "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,cloudcover,windspeed_10m,winddirection_10m,uv_index,is_day"

    private val weeklyData =
        "precipitation_sum,temperature_2m_max,temperature_2m_min,weathercode,windspeed_10m_max"

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String,
    ): Flow<Resources<DailyDataLocal>> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDateFormatted = dateFormat.format(dateFormat.parse(startDate))
        val endDateFormatted = dateFormat.format(dateFormat.parse(endDate))
        return flow {
            try {
                emit(Resources.Loading<DailyDataLocal>())
                val response = service.getDaily(
                    lat,
                    lon,
                    dailyData,
                    "UTC",
                    startDateFormatted,
                    endDateFormatted
                )
                emit(Resources.Success<DailyDataLocal>(response.toDailyDataLocal()!!))
            } catch (e: Exception) {
                emit(Resources.Error<DailyDataLocal>(e.message.toString()))
            }
        }
    }

    suspend fun getHomeWeather(
        lat: Double,
        lon: Double,
    ): Flow<Resources<WeeklyDataLocal>> {
        return flow {
            try {
                emit(Resources.Loading<WeeklyDataLocal>())
                val response = service.getWeekly(lat, lon, weeklyData, "UTC")
                emit(Resources.Success<WeeklyDataLocal>(response.toWeeklyDataLocal()!!))
            } catch (e: Exception) {
                emit(Resources.Error<WeeklyDataLocal>(e.message.toString()))
            }
        }
    }
}
