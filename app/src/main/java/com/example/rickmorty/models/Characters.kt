package com.example.rickmorty.models

import kotlinx.serialization.Serializable
@Serializable
data class Characters (
    val info: Info,
    val results: List<Character>
)

@Serializable
data class Info (
    val count: Long,
    val pages: Long,
    val next: String? = null,
    val prev: String? = null
)

@Serializable
data class Location (
    val name: String,
    val url: String
)
