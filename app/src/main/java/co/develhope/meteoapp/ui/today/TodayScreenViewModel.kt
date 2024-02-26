package co.develhope.meteoapp.ui.today

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.DailyDataLocal
import co.develhope.meteoapp.network.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayScreenViewModel
    @Inject constructor(
        private val repo: WeatherRepo,
) : ViewModel() {
    private val _dailyDataLocal = MutableLiveData<DailyDataLocal?>()
    val dailyDataLocal: LiveData<DailyDataLocal?> get() = _dailyDataLocal



     fun getDaily(
        lat: Double,
        lon: Double,
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch {
            val response = repo.getWeather(lat, lon, startDate, endDate)
            if (response != null) {
              _dailyDataLocal.postValue(response)
            } else {
                Log.e("NETWORK ERROR", "Couldn't achieve network call. (Today Screen)")
            }
        }
    }





}