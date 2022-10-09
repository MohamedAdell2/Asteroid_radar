package com.udacity.asteroidradar.Network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface NasaApiServices {
    @GET("neo/rest/v1/feed")
    fun getDataAsync(
        @Query("start_date") startDate:String,
        @Query("end_date") endDate:String,
        @Query("api_key") key:String
    ) : Deferred<String>

    @GET("planetary/apod")
    fun getpictureOfTheDayAsync(@Query("api_key") key: String):Deferred<PictureOfDay>
}

object Api {
    val retrofitServices :NasaApiServices by lazy {
        retrofit.create(NasaApiServices::class.java)
    }
}