package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.udacity.asteroidradar.work.AsteroidWorkManger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWorkManger()
    }

    private fun setWorkManger() =
        applicationScope.launch {
            val constrains = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<AsteroidWorkManger>(
                1 , TimeUnit.DAYS)
                .setConstraints(constrains)
                .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                AsteroidWorkManger.work_name , ExistingPeriodicWorkPolicy.REPLACE
                    ,workRequest)
    }
}
