package com.example.project.presentation.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.FragmentStudentsBinding

import com.example.project.presentation.Main.StudentsAdapter.StudentsAdapter
import com.example.project.presentation.MainApplication
import com.example.project.presentation.ViewModelFactory
import javax.inject.Inject


class StudentsFragment : Fragment() {

    private lateinit var viewModel : StudentsViewModel
    private lateinit var studentsAdapter: StudentsAdapter

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,viewModelFactory)[StudentsViewModel::class.java]

        studentsAdapter = StudentsAdapter()
        binding.rvStudents.adapter = studentsAdapter

        viewModel.getStudents().observe(viewLifecycleOwner){
            Log.d("checkshit1", it.toString())
            studentsAdapter.submitList(it)
        }
    }
}