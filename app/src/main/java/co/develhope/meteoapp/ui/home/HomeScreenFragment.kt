package co.develhope.meteoapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.data.domain.toWeekItems
import co.develhope.meteoapp.databinding.FragmentHomeScreenBinding
import co.develhope.meteoapp.network.WeatherRepo
import co.develhope.meteoapp.ui.home.adapter.WeekAdapter
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import kotlinx.coroutines.launch


class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val repo = WeatherRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        getWeekly(latitude!!, longitude!!)
        setupAdapter()

    }

    private fun setupAdapter() {

        binding.homeRecyclerView.adapter = WeekAdapter(listOf()) {}

    }

    private fun getWeekly(lat: Double, lon: Double) {
        binding.homeProgress.visibility = View.VISIBLE

        lifecycleScope.launch {
            val response = repo.getHomeWeather(lat, lon)
            if (response != null) {
                _binding?.homeProgress?.visibility = View.GONE
                (_binding?.homeRecyclerView?.adapter as? WeekAdapter)?.setNewList(response.toWeekItems(requireContext()))
                Log.i("NETWORK DATA", "$response")
            } else {
                findNavController().navigate(R.id.search_screen)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
