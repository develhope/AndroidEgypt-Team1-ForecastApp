package co.develhope.meteoapp.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.HourlyForecast
import co.develhope.meteoapp.databinding.FragmentTodayScreenBinding
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems
import co.develhope.meteoapp.ui.today.adapter.TodayAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

import org.threeten.bp.OffsetDateTime

@AndroidEntryPoint
class TodayScreenFragment : Fragment() {

    private var _binding: FragmentTodayScreenBinding? = null
    private val viewModel:TodayViewModel by viewModels()
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTodayScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.dailyData.collect{
                updateUI(it)
            }
        }

        val dataSearches = Data.getSearchCity(requireContext())
        var longitude = DataSearches.ItemSearch(
            longitude = 0.0,
            latitude = 0.0,
            recentCitySearch = "",
            admin1 = ""
        ).longitude
        if (dataSearches is DataSearches.ItemSearch) {
            longitude = dataSearches.longitude
        }
        var latitude = DataSearches.ItemSearch(
            longitude = 0.0,
            latitude = 0.0,
            recentCitySearch = "",
            admin1 = ""
        ).latitude
        if (dataSearches is DataSearches.ItemSearch) {
            latitude = dataSearches.latitude
        }
        val currentDate = org.threeten.bp.OffsetDateTime.now().format(org.threeten.bp.format.DateTimeFormatter.ofPattern("YYYY-MM-d"))
        Log.d("DATE:", currentDate)
        // Fetch daily weather data
        if (latitude != null&&longitude != null) {
            viewModel.getDaily(latitude, longitude, currentDate, currentDate)
        }

    }

    private fun setupAdapter(forecastItems:List<HourlyForecastItems>) {
        binding.todayRecyclerview.adapter = TodayAdapter(forecastItems)
    }


    private fun updateUI(dailyData: DailyDataLocal) {
        val forecastItems = dailyData.toHourlyForecastItems()
        setupAdapter(forecastItems)
    }

    private fun DailyDataLocal?.toHourlyForecastItems(): List<HourlyForecastItems> {

        val filteredList = this?.filter {
            it.time.isEqual(OffsetDateTime.now()) || it.time.isAfter(OffsetDateTime.now())
        }

        val newList = mutableListOf<HourlyForecastItems>()

        newList.add(
            HourlyForecastItems.Title(
                Data.getCityLocation(requireContext()),
                OffsetDateTime.now()
            )
        )

        filteredList?.forEach { hourly ->
            newList.add(
                HourlyForecastItems.Forecast(
                    HourlyForecast(
                        date = hourly.time,
                        hourlyTemp = hourly.temperature2m?.toInt() ?: 0,
                        possibleRain = hourly.rainChance ?: 0,
                        apparentTemp = hourly.apparentTemperature?.toInt() ?: 0,
                        uvIndex = hourly.uvIndex?.toInt() ?: 0,
                        humidity = hourly.humidity ?: 0,
                        windDirection = hourly.windDirection.toString(),
                        windSpeed = hourly.windSpeed?.toInt() ?: 0,
                        cloudyness = hourly.cloudCover ?: 0,
                        rain = hourly.rain?.toInt() ?: 0,
                        forecastIndex = hourly.weathercode ?: 0,
                        isDay = hourly.isDay ?: 0

                    )
                )
            )
        }
        return newList
    }
}