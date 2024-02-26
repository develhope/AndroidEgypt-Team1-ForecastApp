package co.develhope.meteoapp.ui.tomorrow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.network.WeatherRepo
import kotlinx.coroutines.launch
import java.io.IOException

class TomorrowViewModel : ViewModel() {

    private val repo = WeatherRepo()

    private val _dailyData = MutableLiveData<DailyDataLocal>()
    val dailyData: LiveData<DailyDataLocal>
        get() = _dailyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun getDaily(lat: Double, lon: Double, startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repo.getWeather(lat, lon, startDate, endDate)
                if (response != null) {
                    _dailyData.value = response!!
                    _isLoading.value = false
                }
                Log.i("NETWORK DATA", "$response")
            } catch (e: IOException) {
                Log.e("NETWORK ERROR", "Couldn't achieve network call ${e.message}")
            }
        }

    }

}