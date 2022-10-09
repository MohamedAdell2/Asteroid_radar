package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class AsteroidWorkManger (appContext :Context, param: WorkerParameters)
    :CoroutineWorker(appContext, param){

    companion object{
        const val work_name = "UPDATE DATA"
    }

    override suspend fun doWork(): Result {
        val db = getDataBase(applicationContext)
        val repo = AsteroidRepository(db)
        return try {
            repo.refreshData()
            Result.success()
        }catch (e:HttpException){Result.retry()}
    }
}