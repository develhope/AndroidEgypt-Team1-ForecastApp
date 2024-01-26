package co.develhope.meteoapp.ui.home.adapter

import co.develhope.meteoapp.data.domain.DailySummaryForecast



sealed class WeekItems(val id : Int){

    data class Today(
        val forecast: DailySummaryForecast
    ): WeekItems(TodayId)
    data class Days(
        val forecast: DailySummaryForecast
    ): WeekItems(DaysId)

    data class HomeTitle(
        val locality: String
    ): WeekItems(HomeTitleId)

    object HomeSubtitle : WeekItems(HomeSubtitleId)
    companion object {
        const val TodayId = 1
        const val DaysId = 2
        const val HomeTitleId = 3
        const val HomeSubtitleId = 4
    }
}

