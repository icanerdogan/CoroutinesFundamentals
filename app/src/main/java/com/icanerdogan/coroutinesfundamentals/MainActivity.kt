package com.icanerdogan.coroutinesfundamentals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.icanerdogan.coroutinesfundamentals.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    var count: Int = 1
    private var userTotal: Int = 0

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        // VIEW MODEL
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getUserData()
        mainActivityViewModel.users.observe(this, Observer { myUsers ->
            myUsers.forEach {
                Log.i("MyTag", "name is ${it.name}")
            }
        })

        mainBinding.btnCount.setOnClickListener {
            mainBinding.tvCount.text = count++.toString()
        }

        mainBinding.btnDownloadUserData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
            }
        }


        // Main Scope içinde yani UI için
        CoroutineScope(Main).launch {
            Log.i("MyTag", "Calculating Started...")
            // IO olduğudnan dolayı arka planda çalışır!
            val stock1 = async(IO) {
                getStock1()
            }
            val stock2 = async(IO) {
                getStock2()
            }

            val total = stock1.await() + stock2.await()

            Log.i("MyTag", "Total is ${total}")
        }

        mainBinding.btnCount.setOnClickListener {
            CoroutineScope(Main).launch {
                // userTotal = UserDataManager().getTotalUserCount()
                userTotal = UserDataManager2().getTotalUserCount()
                mainBinding.tvUserMessage.text = userTotal.toString()
            }
            Toast.makeText(this, "$userTotal", Toast.LENGTH_SHORT).show()

        }

    }

    private suspend fun downloadUserData() {
        withContext(Dispatchers.Main) {
            for (i in 1..200000) {
                mainBinding.tvUserMessage.text =
                    "Downloading user $i in ${Thread.currentThread().name}"
                delay(100)
            }
        }
    }
}

private suspend fun getStock1(): Int {
    delay(10000)
    Log.i("MyTag", "Stock 1 Returned!")
    return 1
}

private suspend fun getStock2(): Int {
    delay(12000)
    Log.i("MyTag", "Stock 2 Returned!")
    return 2
}


private suspend fun getStock3(): Int {
    delay(15000)
    Log.i("MyTag", "Stock 3 Returned!")
    return 3
}

private suspend fun getStock4(): Int {
    delay(9000)
    Log.i("MyTag", "Stock 4 Returned!")
    return 4
}