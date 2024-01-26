package co.develhope.meteoapp.ui.today

import co.develhope.meteoapp.data.domain.WeatherIcon

fun getWeatherIconbasedonId(isDay: Int, forecastIndex: Int): WeatherIcon {

    if (isDay == 1) {
        when (forecastIndex) {
            in 0..1 -> return WeatherIcon.SUN
            in 2..48 -> return WeatherIcon.SUN_CLOUD
            in 49..200 -> return WeatherIcon.RAIN
            else -> return WeatherIcon.NOTAVAILABLE
        }
    } else if (isDay == 0) {
        return WeatherIcon.MOON
    } else{
        throw IllegalArgumentException("Invalid value for isDay")
    }
}

//       when(forecastIndex){
////           in 0..1-> WeatherIcon.SUN
////            in 2..48 -> WeatherIcon.SUN_CLOUD
////            in 49..200 -> WeatherIcon.RAIN
////            else -> WeatherIcon.NOTAVAILABLE
//        }
