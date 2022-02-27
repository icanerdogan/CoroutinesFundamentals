package com.icanerdogan.coroutinesfundamentals

import kotlinx.coroutines.*

// UNSTRUCTURED CONCURRENCY
class UserDataManager {
    suspend fun getTotalUserCount() : Int{
        var count  = 0

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            count =  50
        }

        val otherScope  = CoroutineScope(Dispatchers.IO).async {
            delay(3000)
            return@async 70
        }

        return count + otherScope.await()
    }
}