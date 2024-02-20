package com.ezyxip.runa.service

interface Decoder {
    companion object{
        val bean: Decoder by lazy { DictDecoder() }
    }
    fun decode(encodedText: String): String
}