package com.example.rickmorty.service

import android.util.Log
import com.example.rickmorty.dao.HttpClient
import com.example.rickmorty.models.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterService {

    private val TAG = "CharacterService"
    companion object {
        val shared = CharacterService()
    }

    fun getAllCharacters(page: Int = 1, callback: (Characters) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            HttpClient.client.getCharacters(page = page).enqueue(object : Callback<Characters> {
                override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                    if (response.body() != null) {
                        callback(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<Characters>, t: Throwable) {
                    Log.d(TAG, t.toString())
                }
            })
        }
    }
}