package com.ezyxip.russhifr.data

import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FileDataStore: DataAdapter {
    val appDir: File by lazy {
        FileDataStoreConfiguration.bean.appDir
    }
    private fun getPassword():String{
        val passFile = File(appDir, "password.d")
        if(!passFile.exists()) return ""
        val input = FileReader(passFile)
        return input.use {
            it.readLines().first()
        }

    }
    override fun isPasswordSet(): Boolean {
        return getPassword().isNotEmpty()
    }

    override fun checkPassword(password: String): Boolean {
        return password == getPassword()
    }

    override fun setPassword(newPassword: String) {
        val passFile = File(appDir, "password.d")
        FileWriter(passFile).use {
            it.write(newPassword)
        }
    }

    override fun getDictionary(): Map<Char, Char> {
        val dictFile = File(appDir, "dictionary")
        if(!dictFile.exists()) return mapOf()
        val input = FileReader(dictFile)
        val res = mutableMapOf<Char, Char>()
        input.use {
            for(i in it.readLines()){
                val splitedLine = i.split("~|~")
                res[splitedLine[0][0]] = splitedLine[1][0]
            }
        }
        return res
    }

    override fun setDictionary(map: Map<Char, Char>) {
        val dictFile = File(appDir, "dictionary")
        val output = FileWriter(dictFile, false)
        output.use {
            for(i in map){
                it.write("${i.key}~|~${i.value}\n")
            }
        }
    }
}