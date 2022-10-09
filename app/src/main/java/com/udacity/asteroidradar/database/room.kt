package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.model.Asteroid

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :today ORDER BY closeApproachDate")
    fun getData(today :String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate = :today ORDER BY closeApproachDate")
    fun getDataToday(today :String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertdata(vararg asteroids: Asteroid)

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate < :today")
    suspend fun deletePreviousData(today: String)
}

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDataBase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDataBase

fun getDataBase(context: Context): AsteroidDataBase {
    synchronized(AsteroidDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, AsteroidDataBase::class.java, "Asteroids"
            ).build()
        }
    }
    return INSTANCE
}