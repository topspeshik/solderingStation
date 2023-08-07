package com.example.project.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.project.data.MainRepository
import com.example.project.data.NotificationWorkerFactory
import com.example.project.di.DaggerApplicationComponent
import javax.inject.Inject


class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: NotificationWorkerFactory


    val component  by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}