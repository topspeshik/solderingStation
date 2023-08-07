package com.example.project.di

import androidx.lifecycle.ViewModel
import com.example.project.presentation.Auth.ViewModels.ForgotPasswordViewModel
import com.example.project.presentation.Auth.ViewModels.LoginViewModel
import com.example.project.presentation.Auth.ViewModels.RegisterViewModel
import com.example.project.presentation.Main.MainViewModel
import com.example.project.presentation.Main.StudentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindRegisterViewModel(viewModel: RegisterViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    fun bindForgotPasswordViewModel(viewModel: ForgotPasswordViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudentsViewModel::class)
    fun bindStudentsViewModel(viewModel: StudentsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}