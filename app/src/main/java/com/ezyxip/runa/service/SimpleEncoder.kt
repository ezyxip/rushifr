package com.ezyxip.runa.service

class SimpleEncoder: Encoder {
    override fun encode(text: String): String {
        return text
    }
}