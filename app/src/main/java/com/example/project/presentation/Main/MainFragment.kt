package com.example.project.presentation.Main


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.FragmentMainBinding

import com.example.project.presentation.Main.MainAdapter.MainAdapter
import com.example.project.presentation.MainApplication
import com.example.project.presentation.ViewModelFactory
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as MainApplication).component
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainAdapter = MainAdapter()
        binding.rvMain.adapter = mainAdapter

        viewModel.notificationFromArduino()
        viewModel.getNotifications().observe(viewLifecycleOwner){
            Log.d("checkshit1", it.toString())
            mainAdapter.submitList(it)
        }

    }


}