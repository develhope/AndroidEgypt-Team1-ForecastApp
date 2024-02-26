package co.develhope.meteoapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.domain.WeeklyDataLocal
import co.develhope.meteoapp.di.TomorrowModule_GetWeatherServiceFactory
import co.develhope.meteoapp.network.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {

    private val _navigationCommand = MutableSharedFlow<Boolean>()
    val navigationCommand = _navigationCommand.asSharedFlow()
    private val _weeklyData = MutableLiveData<WeeklyDataLocal>()
    val weeklyData: LiveData<WeeklyDataLocal>
        get() = _weeklyData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getWeekly(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _weeklyData.value = repo.getHomeWeather(lat, lon)
                Log.i("NETWORK DATA", "${_weeklyData.value}")
                _isLoading.value = false
            } catch (e: Exception) {
                Log.i("NETWORK Error", "${e.message}")
                _navigationCommand.emit(true)
            }
        }
    }
}