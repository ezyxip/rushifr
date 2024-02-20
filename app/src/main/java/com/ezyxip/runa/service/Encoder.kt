package com.ezyxip.runa.service

interface Encoder {
    companion object{
        val bean = DictEncoder()
    }
    fun encode(text: String): String
}