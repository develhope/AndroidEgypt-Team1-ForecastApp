package co.develhope.meteoapp.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.databinding.FragmentSearchScreenBinding
import co.develhope.meteoapp.network.SearchRepo
import co.develhope.meteoapp.ui.MainActivity
import co.develhope.meteoapp.ui.search.adapter.DataSearchAdapter
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.Locale


class SearchScreenFragment : Fragment() {

    private var _binding: FragmentSearchScreenBinding? = null
    private val binding get() = _binding!!
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var repo = SearchRepo()

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val locationManager =
                    ContextCompat.getSystemService(requireContext(), LocationManager::class.java)
                if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                    if (fusedLocationClient == null) fusedLocationClient = LocationServices
                        .getFusedLocationProviderClient(requireActivity())

                    fusedLocationClient
                        ?.lastLocation
                        ?.addOnSuccessListener { location ->
                            if (location != null) {
                                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                                val loc =
                                    geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                Data.saveSearchCity(
                                    requireContext(),
                                    DataSearches.ItemSearch(
                                        recentCitySearch = loc.firstOrNull()?.locality ?: "---",
                                        admin1 = loc.firstOrNull()?.adminArea ?: "---",
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                )

                                findNavController().navigate(R.id.home_screen)
                                (activity as? MainActivity)?.setBottomNavVisibility(View.VISIBLE)
                            } else {
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Posizione non trovata")
                                    .setMessage("Non siamo riusciti a trovare la tua posizione, cerca manualmente la tua città.")
                                    .setPositiveButton("OK") { _, _ ->
                                    }
                                    .show()
                            }
                        }
                        ?.addOnFailureListener {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Posizione non trovata")
                                .setMessage("Non siamo riusciti a trovare la tua posizione, cerca manualmente la tua città.")
                                .setPositiveButton("OK") { _, _ ->
                                }
                                .show()
                        }
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("GPS disattivato")
                        .setMessage("La tua localizzazione è disattivata, vuoi attivarla?")
                        .setPositiveButton("SI") { _, _ ->
                            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            startActivity(intent)
                        }
                        .setNegativeButton("NO") { _, _ ->
                            findNavController().navigate(R.id.search_screen)
                        }
                        .show()
                }
            } else {
                findNavController().navigate(R.id.search_screen)
            }
        }

//    private var selectedSearchItem: DataSearches? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val mainActivity = requireActivity() as MainActivity

        binding.searchRecyclerView.adapter = DataSearchAdapter(
            searchList = Data.getRecentSearches(requireContext()),
            onClick = { model ->
                Data.saveSearchCity(requireContext(), model)
                //moveSelectedSearchToTop(model)
                clearAutoCompleteTextView()
//                searchViewModel.clearSearch()
                clearSearch()
                (activity as MainActivity).setBottomNavVisibility(View.VISIBLE)
                findNavController().navigate(R.id.action_search_to_home)
            }
        )

//        observerSearch()

        binding.xIconClick.setOnClickListener {
            clearAutoCompleteTextView()
            Log.d("X ICON CLICKED", "CLICK CLICK CLICK !")
        }

        binding.locationImage.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        binding.searchAutoCompleteTextView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) {
                        binding.xIconClick.visibility = View.GONE

                        (binding.searchRecyclerView.adapter as? DataSearchAdapter)?.setNewList(
                            Data.getRecentSearches(
                                requireContext()
                            )
                        )
                    } else {
                        binding.xIconClick.visibility = View.VISIBLE

//                        searchViewModel.getPlaces(s.toString())
                        getPlaces(s.toString())
                    }
                }
            })
    }

//    private fun observerSearch() {
//        searchViewModel.cityHints.observe(viewLifecycleOwner) { hints ->
//            hints
//                ?.toDataSearches()
//                ?.let { newList ->
//                    (binding.searchRecyclerView.adapter as? DataSearchAdapter)
//                        ?.setNewList(newList)
//                }
//        }
//    }

    private fun clearAutoCompleteTextView() {
        binding.searchAutoCompleteTextView.setText("")
        binding.xIconClick.visibility = View.GONE
        (binding.searchRecyclerView.adapter as? DataSearchAdapter)?.setNewList(
            Data.getRecentSearches(
                requireContext()
            )
        )
    }

    /*    private fun moveSelectedSearchToTop(selectedItem: DataSearches) {
            Data.moveSearchToTop(selectedItem)
        }*/

    private fun clearSearch() {
//        _cityHints.postValue(null)
        (binding.searchRecyclerView.adapter as? DataSearchAdapter)?.setNewList(emptyList())
    }

    fun getPlaces(place: String) {
//        Log.d("GET PLACES", place)
        lifecycleScope.launch {
            val response = repo.getSearch(place)
            if (response != null) {
//                _cityHints.postValue(response)
                response.toDataSearches().let { newList ->
                    (binding.searchRecyclerView.adapter as? DataSearchAdapter)
                        ?.setNewList(newList)
                }
                response.forEach {
                    Log.d(
                        "DATA",
                        "${it.admin1},${it.name}, ${it.latitude}, ${it.longitude} "
                    )
                }
            } else {
                Log.e("ERROR", "network error ")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

