package com.movieapp.flashapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.movieapp.flashapp.R
import com.movieapp.flashapp.databinding.FragmentHomeBinding
import com.movieapp.flashapp.viewmodel.SosAlertsViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.colorLightButton.setOnClickListener {
            val action=HomeFragmentDirections.actionNavHomeToNavGallery()
            findNavController().navigate(action)
        }
        binding.flashLightButton.setOnClickListener {
            val action=HomeFragmentDirections.actionNavHomeToNavSlideshow()
            findNavController().navigate(action)
        }
        binding.sosAlertButton.setOnClickListener {
            val action=HomeFragmentDirections.actionNavHomeToSosAlertsFragment()
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}