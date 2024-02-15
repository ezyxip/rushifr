package com.ezyxip.russhifr.data

interface DataAdapter {
    companion object{
        val defaultAdapter = DefaultDataSource()
    }

    fun isPasswordSet(): Boolean {
        return false
    }

    fun checkPassword(password: String): Boolean {
        return true
    }

    fun setPassword(newPassword: String){}

}