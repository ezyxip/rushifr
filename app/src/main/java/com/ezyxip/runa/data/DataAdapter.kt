package com.ezyxip.runa.data

interface DataAdapter {
    companion object{
        val bean by lazy { FileDataStore() }
    }

    fun isPasswordSet(): Boolean

    fun checkPassword(password: String): Boolean

    fun setPassword(newPassword: String)

    fun getDictionary(): Map<Char, Char>
    fun setDictionary(map: Map<Char, Char>)
}