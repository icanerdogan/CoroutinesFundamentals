package com.icanerdogan.coroutinesfundamentals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icanerdogan.coroutinesfundamentals.model.User
import com.icanerdogan.coroutinesfundamentals.model.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {
    /*private val myJob = Job()
    private val myScope = CoroutineScope(IO + myJob)
     */
    private var userRepository = UserRepository()
    var users : MutableLiveData<List<User>> = MutableLiveData()

    fun getUserData(){
        //myScope.launch {}
        viewModelScope.launch {
            var result : List<User>? = null
            withContext(IO) {
                result = userRepository.getUsers()
            }
            users.value = result
        }
    }

    /*
    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }
     */
}