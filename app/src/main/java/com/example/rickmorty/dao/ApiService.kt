package com.example.rickmorty.dao

import com.example.rickmorty.models.Characters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    fun getCharacters(@Query("page") page: Int): Call<Characters>
}