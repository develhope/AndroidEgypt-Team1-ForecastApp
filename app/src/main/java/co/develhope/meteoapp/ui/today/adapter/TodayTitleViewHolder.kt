package co.develhope.meteoapp.ui.today.adapter

import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.databinding.TitleTodayScreenBinding
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems.Title
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class TodayTitleViewHolder(private val binding: TitleTodayScreenBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.capitalize() }
    }

    fun onBind(item: Title) {
        val currentDate = LocalDate.now()

        val formattedDate = capitalizeWords(
            currentDate.format(
                DateTimeFormatter.ofPattern(
                    "EEEE d MMMM",
                    Locale.ITALIAN
                )
            )
        )

        binding.todayLocationTv.text = item.todayLocation
        binding.todaySubtitleLongTv.text = formattedDate
        binding.todayConditionTv.text = Data.weatherCodetoCondition(Data.getTodayCondition()!!)
    }
}