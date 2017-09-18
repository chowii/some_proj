package com.soho.sohoapp.data.singletons

import com.soho.sohoapp.data.models.User

/**
 * Created by Jovan on 15/9/17.
 */

class SharedUser {
    var user: User? = null
        set(value) {
            field = value
        }

    companion object {

        private var uniqueInstance: SharedUser? = null
        fun getInstance(): SharedUser {
            if (uniqueInstance == null) {
                uniqueInstance = SharedUser()
                return uniqueInstance as SharedUser
            }
            return uniqueInstance as SharedUser
        }
    }
}