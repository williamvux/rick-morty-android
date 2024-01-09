package com.example.rickmorty.dao

import com.example.rickmorty.models.Characters
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<Characters>
}