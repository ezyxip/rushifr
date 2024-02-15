package com.ezyxip.russhifr.service

interface Encoder {
    companion object{
        val defaultEncoder = SimpleEncoder()
    }
    fun encode(text: String): String
}