package co.develhope.meteoapp.network

import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.data.dto.toDailyDataLocal
import co.develhope.meteoapp.data.dto.toWeeklyDataLocal
import java.text.SimpleDateFormat

class WeatherRepo {
    private val dailyData =
        "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,cloudcover,windspeed_10m,winddirection_10m,uv_index,is_day"

    private val weeklyData =
        "precipitation_sum,temperature_2m_max,temperature_2m_min,weathercode,windspeed_10m_max"

    suspend fun getWeather(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String,
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
