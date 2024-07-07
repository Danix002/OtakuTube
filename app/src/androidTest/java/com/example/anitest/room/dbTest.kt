package com.example.anitest.room

import org.junit.After
import org.junit.Before
import org.junit.Test

class dbTest {

    @Before
    fun init() {
        println("Inizializzazione")
    }

    @Test
    fun test() {
        println("Testing")
    }

    @After
    fun close() {
        println("de-Inizializzazione")
    }
}