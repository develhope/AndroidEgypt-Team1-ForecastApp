package co.develhope.meteoapp.ui.welcome_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.databinding.FragmentWelcomeScreenBinding
import co.develhope.meteoapp.ui.MainActivity
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale


class WelcomeScreen : Fragment() {
    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding get() = _binding!!
    private var fusedLocationClient: FusedLocationProviderClient? = null

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val locationManager =
                    getSystemService(requireContext(), LocationManager::class.java)
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
                                findNavController().navigate(R.id.search_screen)
                            }
                        }
                        ?.addOnFailureListener {
                            findNavController().navigate(R.id.search_screen)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        )

        binding.welcomeButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

    }
}
