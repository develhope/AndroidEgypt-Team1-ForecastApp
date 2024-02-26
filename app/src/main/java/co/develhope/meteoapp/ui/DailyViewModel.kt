package co.develhope.meteoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.data.utills.Resources
import co.develhope.meteoapp.network.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {

    private val _dailyData = MutableStateFlow<DailyDataLocal>(DailyDataLocal())
    val dailyData = _dailyData.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()


    fun getDaily(lat: Double, lon: Double, startDate: String, endDate: String) {
        viewModelScope.launch {
            repo.getWeather(lat, lon, startDate, endDate).collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _isLoading.emit(true)
                    }

                    is Resources.Success -> {
                        _dailyData.emit(result.data ?: DailyDataLocal())
                        _isLoading.emit(false)

                    }

                    is Resources.Error -> {
                        Log.e("NETWORK ERROR", "Couldn't achieve network call ${result.message}")
                        _isLoading.emit(false)

                    }
                }
            }
        }

    }

}