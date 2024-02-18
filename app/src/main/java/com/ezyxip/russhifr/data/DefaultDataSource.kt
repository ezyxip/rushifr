package com.ezyxip.russhifr.data

class DefaultDataSource: DataAdapter {
    private var password: String? = "pass"
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
        val res = mutableMapOf<Char, Char>()
        var currentChar = 'a'
        for(i in 'b'..'z'){
            res[currentChar] = i
            currentChar = i
        }
        res['z'] = 'a'
        return res
    }


}