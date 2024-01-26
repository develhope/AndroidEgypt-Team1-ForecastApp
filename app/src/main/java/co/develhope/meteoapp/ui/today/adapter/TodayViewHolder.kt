package co.develhope.meteoapp.ui.today.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.ListTodayScreenBinding
import co.develhope.meteoapp.data.domain.setWeatherIcon
import co.develhope.meteoapp.ui.today.getWeatherIconbasedonId
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems.Forecast
import org.threeten.bp.format.DateTimeFormatter

open class TodayViewHolder(val binding: ListTodayScreenBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(
        item: Forecast,
        position: Int,
        openedItems: MutableList<Int>,
        onClick: () -> Unit
    ) {

        binding.todayTimeTv.text = item.forecast.date.format(DateTimeFormatter.ofPattern("HH:mm"))

       binding.weatherTodayImage.setWeatherIcon(getWeatherIconbasedonId(item.forecast.isDay,item.forecast.forecastIndex))
        binding.degreesToday.text = item.forecast.hourlyTemp.toString().plus("°")
        binding.rainChanceTv.text = item.forecast.possibleRain.toString().plus("%")
        binding.perceivedDegreesToday.text = item.forecast.apparentTemp.toString().plus("°")
        binding.uvIndexToday.text = item.forecast.uvIndex.toString().plus("/10")
        binding.humidityPercentageToday.text = item.forecast.humidity.toString().plus("%")
        binding.windSpeedToday.text = item.forecast.windDirection.plus((" ").plus(item.forecast.windSpeed.toString().plus("Km/h")))
        binding.coverageToday.text = item.forecast.cloudyness.toString().plus("%")
        binding.rainToday.text = item.forecast.rain.toString().plus("cm")

        // Toggle visibility of the CardView
        if (position in openedItems) {
            binding.arrowImageToday.rotation = 180f
            binding.todayCardview.visibility = View.VISIBLE
        } else {
            binding.arrowImageToday.rotation = 0f
            binding.todayCardview.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            if (position in openedItems) openedItems.remove(position)
            else openedItems.add(position)

            onClick()
        }
    }
}