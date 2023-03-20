package com.example.binapplication.network

import com.example.binapplication.data.JSON.CardData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://lookup.binlist.net"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BinApiService {
    @GET("{number}")
    suspend fun getCardData(@Path("number") number: String): CardData
}

object BinApi{
    val retrofitService: BinApiService by lazy {
        retrofit.create(BinApiService::class.java) }
}