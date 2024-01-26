package co.develhope.meteoapp.data.domain

import org.threeten.bp.OffsetDateTime

data class HourlyForecast(
        val date: OffsetDateTime,
        val hourlyTemp: Int,
        val possibleRain: Int,
        val apparentTemp: Int, //temp percepita
        val uvIndex: Int,
        val humidity: Int,
        val windDirection: String,
        val windSpeed: Int,
        val cloudyness: Int,
        val rain: Int,
        val forecastIndex: Int,
        val isDay: Int
    )