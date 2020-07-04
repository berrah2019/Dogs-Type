package com.example.exploredogs.models

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogApiService {
    private  val baseUrl="https://raw.githubusercontent.com"
    private val api=Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogApi::class.java)
    fun getDogs():Single<List<Dogbreed>>{
      return  api.getDogs()
    }
}