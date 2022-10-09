package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Network.Api
import com.udacity.asteroidradar.Network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.model.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepository(private val dataBase: AsteroidDataBase) {

    private val date = getDate()
    val listAll :LiveData<List<Asteroid>> = dataBase.asteroidDao.getData(date.first)
    val listToday :LiveData<List<Asteroid>> = dataBase.asteroidDao.getDataToday(date.first)

    suspend fun refreshData (){
        withContext(Dispatchers.IO){
            try {
                dataBase.asteroidDao.deletePreviousData(date.first)
                val newList = Api.retrofitServices.getDataAsync(date.first, date.second
                    , BuildConfig.NASA_API_KEY).await()
                val newAsteroid = parseAsteroidsJsonResult(JSONObject(newList))
                newAsteroid.forEach { dataBase.asteroidDao.insertdata(it) }
            }catch (e:Exception){
                Log.e("Repository" , e.message+"")
            }
        }
    }


    private fun getDate(): Pair<String, String> {
        val date = LocalDateTime.now()
        val formatted = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
        val currentTime = date.format(formatted)
        val nextWeek = date.plusWeeks(1).format(formatted)
        return currentTime to nextWeek
    }

}