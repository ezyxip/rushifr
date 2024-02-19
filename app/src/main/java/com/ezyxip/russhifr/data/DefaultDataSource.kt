package com.ezyxip.russhifr.data

class DefaultDataSource: DataAdapter {
    private var password: String? = ""
    private var dict: MutableMap<Char, Char>
    init{
        val res = mutableMapOf<Char, Char>()
        var currentChar = 'a'
        for(i in 'b'..'z'){
            res[currentChar] = i
            currentChar = i
        }
        res['z'] = 'a'
        dict = res
    }
    override fun isPasswordSet(): Boolean {
        return !password.isNullOrEmpty()
    }

    override fun checkPassword(password: String): Boolean {
        return password == this.password
    }

    override fun setPassword(newPassword: String) {
        password = newPassword
    }

    override fun getDictionary(): Map<Char, Char> {
        return dict
    }

    override fun setDictionary(map: Map<Char, Char>) {
        dict = map.toMutableMap()
    }


}