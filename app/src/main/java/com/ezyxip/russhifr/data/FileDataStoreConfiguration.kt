package com.ezyxip.russhifr.data

import java.io.File

interface FileDataStoreConfiguration {
    companion object{
        val bean: FileDataStoreConfiguration = object : FileDataStoreConfiguration{
            override lateinit var appDir: File

        }
    }
    var appDir: File
}