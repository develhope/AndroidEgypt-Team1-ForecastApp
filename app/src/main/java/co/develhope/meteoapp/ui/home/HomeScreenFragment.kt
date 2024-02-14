package co.develhope.meteoapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.data.domain.toWeekItems
import co.develhope.meteoapp.databinding.FragmentHomeScreenBinding
import co.develhope.meteoapp.network.WeatherRepo
import co.develhope.meteoapp.ui.home.adapter.WeekAdapter
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val weeklyViewModel: WeeklyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        weeklyViewModel.getWeekly(latitude!!, longitude!!)
        setupAdapter()
        setupObservers()

    }

    private fun setupAdapter() {
        binding.homeRecyclerView.adapter = WeekAdapter(listOf()) {}
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            weeklyViewModel.weeklyData.collect {
                (_binding?.homeRecyclerView?.adapter as? WeekAdapter)?.setNewList(
                    it.toWeekItems(
                        requireContext()
                    )
                )
            }
        }
        lifecycleScope.launch {
            weeklyViewModel.isLoading.collectLatest {
                _binding?.homeProgress?.isVisible = it
            }
        }
        lifecycleScope.launch {
            weeklyViewModel.navigationCommand.collectLatest { isTrue ->
                findNavController().navigate(R.id.search_screen)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}
