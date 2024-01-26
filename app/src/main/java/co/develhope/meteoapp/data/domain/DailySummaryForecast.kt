package co.develhope.meteoapp.data.domain
import co.develhope.meteoapp.R
import co.develhope.meteoapp.R.drawable
import org.threeten.bp.OffsetDateTime


data class DailySummaryForecast(
    val date: OffsetDateTime,
    val minTemperature: Int,
    val maxTemperature: Int,
    val weatherIcon: Int,
    val precipitation: Int,
    val windSpeed: Int
)




