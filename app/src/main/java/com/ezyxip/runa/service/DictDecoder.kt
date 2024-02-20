package com.ezyxip.runa.service

import com.ezyxip.runa.data.DataAdapter

class DictDecoder: Decoder {
    override fun decode(encodedText: String): String {
        val dict = getRevertDict()
        return encodedText.map { e -> dict[e] ?: e }.joinToString("")
    }
    private fun getRevertDict(): Map<Char, Char>{
        val dict = DataAdapter.bean.getDictionary()
        return dict.map { e -> e.value to e.key }.toMap()
    }
}