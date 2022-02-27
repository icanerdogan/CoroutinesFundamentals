package com.icanerdogan.coroutinesfundamentals.model

import kotlinx.coroutines.delay

class UserRepository {
    suspend fun getUsers() : List<User>{
        delay(8000)
        val users : List<User> = listOf(
            User(1, "İbrahim"),
            User(2, "Can"),
            User(3, "Erdoğan")

        )
        return users
    }
}