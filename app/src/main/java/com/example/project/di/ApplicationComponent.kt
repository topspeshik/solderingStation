package com.example.project.di

import android.app.Application
import com.example.project.presentation.Auth.ForgotPasswordFragment
import com.example.project.presentation.Auth.LoginFragment
import com.example.project.presentation.Auth.RegisterFragment
import com.example.project.presentation.Main.MainFragment
import com.example.project.presentation.Main.StudentsFragment
import com.example.project.presentation.MainApplication
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: ForgotPasswordFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegisterFragment)

    fun inject(fragment: StudentsFragment)

    fun inject(fragment: MainFragment)

    fun inject(app: MainApplication)


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}