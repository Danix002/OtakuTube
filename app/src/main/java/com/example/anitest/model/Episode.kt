package com.example.anitest.model

data class Episode(
    val index: Int,
    val ep: List<Ep>
)

data class Ep(
    val name: String,
    val link: String
)
