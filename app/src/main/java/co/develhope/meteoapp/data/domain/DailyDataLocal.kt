package co.develhope.meteoapp.data.domain

import co.develhope.meteoapp.data.domain.DailyDataLocal.HourlyLocal
import org.threeten.bp.OffsetDateTime


class DailyDataLocal() : ArrayList<HourlyLocal>() {
    data class HourlyLocal(
        val apparentTemperature: Double?,
        val cloudCover: Int?,
        val rainChance: Int?,
        val humidity: Int?,
        val uvIndex: Double?,
        val rain: Double?,
        val temperature2m: Double?,
        val time: OffsetDateTime,
        val weathercode: Int?,
        val windSpeed: Double?,
        val windDirection: String?,
        val isDay: Int?
    )
}
