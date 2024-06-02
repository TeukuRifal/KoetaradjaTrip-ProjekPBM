package com.pbm.koetaradjatrip.network


import com.pbm.koetaradjatrip.models.Place
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiClient {
    @GET("your_endpoint")
    fun getPlaces(): Call<List<Place>>

    companion object {
        private const val BASE_URL = "https://your.api.url/"

        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiClient::class.java)
        }
    }
}
