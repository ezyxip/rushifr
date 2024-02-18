package com.ezyxip.russhifr.service

import com.ezyxip.russhifr.data.DataAdapter

class DictEncoder: Encoder {
    private val dict: Map<Char, Char> = DataAdapter.defaultAdapter.getDictionary()

    override fun encode(text: String): String {
        var res = ""
        for (i in text){
            val increment = dict[i.lowercaseChar()] ?: i
            res += uniform(i, increment)
        }
        return res
    }
}

fun uniform(pattern: Char, second: Char): Char{
    return if(pattern.isLowerCase())
            second.lowercaseChar()
        else
            second.uppercaseChar()
}