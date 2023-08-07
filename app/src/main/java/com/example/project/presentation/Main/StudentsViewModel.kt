package com.example.project.presentation.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.project.data.MainRepository
import com.example.project.domain.User
import javax.inject.Inject

class StudentsViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    fun getStudents(): LiveData<List<User>> {
        return mainRepository.getStudents()
    }
}