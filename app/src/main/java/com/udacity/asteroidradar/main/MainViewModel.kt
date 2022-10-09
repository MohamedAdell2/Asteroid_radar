package com.udacity.asteroidradar.main

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Network.Api
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class DataBaseFilter {ALL ,Today }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDataBase(application.applicationContext)
    private val repository = AsteroidRepository(database)

    val asteroids:MutableLiveData<LiveData<List<Asteroid>>> = MutableLiveData(
        repository.listAll)

    private val _picture =MutableLiveData<PictureOfDay>()
    val picture:LiveData<PictureOfDay>
    get() = _picture

    init {
        viewModelScope.launch {
           loadFromRepo()
        }
    }

    fun filterResult (filter: DataBaseFilter){
        asteroids.value = when(filter){
            DataBaseFilter.Today -> repository.listToday
            else -> repository.listAll
        }
    }

    private suspend fun loadFromRepo(){
        try {
        _picture.value = Api.retrofitServices
            .getpictureOfTheDayAsync(BuildConfig.NASA_API_KEY).await() }
        catch (e:Exception){
            Toast.makeText( getApplication<Application>()
                .applicationContext,"No Internet connection"
                , Toast.LENGTH_SHORT).show()
        }
        repository.refreshData()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}