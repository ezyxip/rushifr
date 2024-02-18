package com.ezyxip.russhifr.service

interface Encoder {
    companion object{
        val bean = DictEncoder()
    }
    fun encode(text: String): String
}