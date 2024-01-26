package co.develhope.meteoapp.ui.error_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.FragmentErrorScreenBinding
import co.develhope.meteoapp.ui.MainActivity
import co.develhope.meteoapp.ui.isNetworkAvailable


class ErrorScreenFragment : Fragment() {

    private var _binding: FragmentErrorScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentErrorScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        binding.errorRetryButton.setOnClickListener {
            if (isNetworkAvailable(requireContext())) {
                findNavController().navigate(R.id.home_screen)

                (activity as? MainActivity)?.setBottomNavVisibility(View.VISIBLE)
            }
        }
    }
}