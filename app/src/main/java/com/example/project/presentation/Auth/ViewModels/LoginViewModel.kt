package com.example.project.presentation.Auth.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.data.AuthRepository
import com.example.project.domain.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {



    private val _stateFlow = MutableStateFlow<AuthState>(AuthState.Loading())
    var stateFlow = _stateFlow.asStateFlow()

    fun login(email: String, password: String){
        authRepository.login(email, password).onEach {
            _stateFlow.value = it
        }.launchIn(viewModelScope)
    }
}