package co.develhope.meteoapp.data.domain

import co.develhope.meteoapp.R


enum class WeatherIcon(val image: Int) {
    SUN(R.drawable.sun),
    RAIN(R.drawable.rain),
    SUN_CLOUD(R.drawable.sun_cloud),
    MOON(R.drawable.moon),
    NOTAVAILABLE(R.drawable.error_image)
}