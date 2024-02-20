package com.ezyxip.runa.data

import androidx.compose.ui.res.stringResource
import com.ezyxip.runa.R
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FileDataStore: DataAdapter {

    companion object{
        const val passwordFileName = "password.data"
        const val dictionaryFileName = "dictionary.dict"
    }

    private val appDir: File by lazy {
        FileDataStoreConfiguration.bean.appDir
    }

    init {
        checkNeededFiles(appDir)
    }
    private fun checkNeededFiles(appDir: File){
        val dictFile = File(appDir, dictionaryFileName)
        if(!dictFile.exists()) {
            val defaultDict = mutableMapOf<Char, Char>()
            var currentChar = 'a'
            for(i in 'b'..'z'){
                defaultDict[currentChar] = i
                currentChar = i
            }
            defaultDict['z'] = 'a'
            val output = FileWriter(dictFile, false)
            output.use {
                for(i in defaultDict){
                    it.write("${i.key}~|~${i.value}\n")
                }
            }
        }
        val passFile = File(appDir, passwordFileName)
        if(!passFile.exists()){
            passFile.createNewFile()
        }
    }
    private fun getPassword():String{
        val passFile = File(appDir, passwordFileName)
        val input = FileReader(passFile)
        return input.use {
            val res = it.readLines()
            if(res.isNotEmpty())
                res.first()
            else
                ""
        }
    }
    override fun isPasswordSet(): Boolean {
        return getPassword().isNotEmpty()
    }

    override fun checkPassword(password: String): Boolean {
        return password == getPassword()
    }

    override fun setPassword(newPassword: String) {
        val passFile = File(appDir, passwordFileName)
        FileWriter(passFile).use {
            it.write(newPassword)
        }
    }

    override fun getDictionary(): Map<Char, Char> {
        val dictFile = File(appDir, dictionaryFileName)
        if(!dictFile.exists()) {
            val res = mutableMapOf<Char, Char>()
            var currentChar = 'a'
            for(i in 'b'..'z'){
                res[currentChar] = i
                currentChar = i
            }
            res['z'] = 'a'
            setDictionary(res)
            return getDictionary()
        }
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
        val dictFile = File(appDir, dictionaryFileName)
        val output = FileWriter(dictFile, false)
        output.use {
            for(i in map){
                it.write("${i.key}~|~${i.value}\n")
            }
        }
    }
}