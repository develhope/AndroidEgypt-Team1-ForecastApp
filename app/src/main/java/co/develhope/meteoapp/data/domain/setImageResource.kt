package co.develhope.meteoapp.data.domain

import android.widget.ImageView
import co.develhope.meteoapp.data.domain.WeatherIcon

fun ImageView.setWeatherIcon(weatherIcon: WeatherIcon) {
    this.setImageResource(weatherIcon.image)
}