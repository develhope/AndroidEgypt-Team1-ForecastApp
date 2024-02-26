package co.develhope.meteoapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import co.develhope.meteoapp.data.domain.SearchDataLocal
import co.develhope.meteoapp.network.SearchRepo
import co.develhope.meteoapp.ui.search.adapter.DataSearchAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: SearchRepo):ViewModel() {
    // Define a MutableLiveData to hold the search response
    private val _searchResponse = MutableLiveData<SearchDataLocal?>()

    // Expose LiveData instead of MutableLiveData
    val searchResponse: LiveData<SearchDataLocal?>
        get() = _searchResponse
    fun getPlaces(place:String){
          viewModelScope.launch {
              val response = repo.getSearch(place)
              if(response !=null){
                _searchResponse.postValue(response)
              }
          }

    }
}