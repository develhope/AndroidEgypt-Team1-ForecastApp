package co.develhope.meteoapp.ui.home.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.databinding.ListHomeScreenBinding
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Today
import co.develhope.meteoapp.data.domain.setWeatherIcon
import co.develhope.meteoapp.ui.today.getWeatherIconbasedonId
import org.threeten.bp.format.DateTimeFormatter

class TodayViewHolder(private val binding: ListHomeScreenBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Today, onClick: (WeekItems) -> Unit) {
        binding.textDayOfWeekList.text = binding.root.context.getString(R.string.today)
        binding.textDayOfWeekList.textSize = 27F
        binding.textDayOfMonthList.text =
            item.forecast.date.format(DateTimeFormatter.ofPattern("dd/MM"))
        binding.textMinNumList.text =
            itemView.resources.getString(R.string.degrees, item.forecast.minTemperature.toString())
        binding.textMaxNumList.text =
            itemView.resources.getString(R.string.degrees,item.forecast.maxTemperature.toString())
        binding.imgSkyList.setWeatherIcon(
            getWeatherIconbasedonId(1,item.forecast.weatherIcon)
        )
        binding.textPrecipNumList.text =
            itemView.resources.getString(R.string.millimetres, item.forecast.precipitation.toString())
        binding.textWindNumList.text =
            itemView.resources.getString(R.string.kilometers_per_hour, item.forecast.windSpeed.toString())
        binding.root.setOnClickListener {
            Data.saveTodayCondition(item.forecast.weatherIcon)
            binding.root.findNavController().navigate(R.id.today_screen)
            onClick(item)
        }
    }
}
