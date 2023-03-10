package org.tensorflow.lite.examples.imageclassification.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.tensorflow.lite.examples.imageclassification.databinding.FragmentFlashScreenBinding


class SplashScreen : Fragment() {
    private var _binding: FragmentFlashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFlashScreenBinding.inflate(inflater, container, false)
        navController = findNavController()
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(SplashScreenDirections.actionSplashScreenToHomeScreen())
        }, 3000)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}