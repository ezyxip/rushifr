package com.ezyxip.runa.service

import com.ezyxip.runa.data.DataAdapter

class DictEncoder: Encoder {

    override fun encode(text: String): String {
        var res = ""
        for (i in text){
            val increment = DataAdapter.bean.getDictionary()[i.lowercaseChar()] ?: i
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