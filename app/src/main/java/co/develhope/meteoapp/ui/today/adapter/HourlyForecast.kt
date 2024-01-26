package co.develhope.meteoapp.ui.today.adapter

import co.develhope.meteoapp.data.domain.HourlyForecast
import org.threeten.bp.OffsetDateTime

sealed class HourlyForecastItems(val type: Int) {

    data class Forecast(
        val forecast: HourlyForecast
    ) : HourlyForecastItems(hourlyForecast)

    data class Title(
        val todayLocation: String,
        val dateTime: OffsetDateTime
    ) : HourlyForecastItems(title)

    companion object {
        const val hourlyForecast = 1
        const val title = 2
    }

}