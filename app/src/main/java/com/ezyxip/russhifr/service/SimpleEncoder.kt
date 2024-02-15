package com.ezyxip.russhifr.service

class SimpleEncoder: Encoder {
    override fun encode(text: String): String {
        return text
    }
}