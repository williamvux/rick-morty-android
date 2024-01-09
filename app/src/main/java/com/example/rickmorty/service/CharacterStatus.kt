package com.example.rickmorty.service

object CharacterStatus {

    val mapStatus = mapOf("Alive" to "Alive", "Dead" to "Dead", "unknown" to "Unknown")
    operator fun get(status: String): String {
        return mapStatus[status] ?: "Unknown";
    }
}