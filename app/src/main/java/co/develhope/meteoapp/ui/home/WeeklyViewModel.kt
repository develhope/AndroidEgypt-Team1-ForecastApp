package co.develhope.meteoapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.data.utills.Resources
import co.develhope.meteoapp.di.TomorrowModule_GetWeatherServiceFactory
import co.develhope.meteoapp.network.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {

    private val _navigationCommand = MutableSharedFlow<Boolean>()
    val navigationCommand = _navigationCommand.asSharedFlow()
    private val _weeklyData = MutableStateFlow<WeeklyDataLocal>(WeeklyDataLocal())
    val weeklyData = _weeklyData.asStateFlow()
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    fun getWeekly(lat: Double, lon: Double) {
        viewModelScope.launch {
            repo.getHomeWeather(lat, lon).collect() { result ->
                when (result) {
                    is Resources.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resources.Success -> {
                        _weeklyData.emit(result.data!!)
                        _isLoading.emit(false)

                    }

                    is Resources.Error -> {
                        Log.i("NETWORK Error", "${result.message}")
                        _navigationCommand.emit(true)
                        _isLoading.emit(false)

                    }
                }
            }
        }
    }
}