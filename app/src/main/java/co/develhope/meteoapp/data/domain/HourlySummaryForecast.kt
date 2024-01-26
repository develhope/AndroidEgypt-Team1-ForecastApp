package co.develhope.meteoapp.data.domain

import org.threeten.bp.OffsetDateTime

data class HourlySummaryForecast(
    val time: OffsetDateTime,
    val weatherImage: Int,
    val degrees: Int,
    val rainChance: Int,
    val perceived: Int,
    val humidity: Int,
    val wind: Int,
    val coverage: Int,
    val rainHeight: Int
)
