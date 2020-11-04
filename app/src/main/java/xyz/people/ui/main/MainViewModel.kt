package xyz.people.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import xyz.people.data.RandomUserService
import xyz.people.data.entity.User
import xyz.people.util.ResultState

class MainViewModel @ViewModelInject constructor(
    private val randomUserService: RandomUserService
) : ViewModel() {

    fun getUser(): LiveData<ResultState<User>> = liveData(Dispatchers.IO) {
        emit(ResultState.Loading())
        try {
            val response = randomUserService.getUser()
            if (response.isSuccessful && response.body() != null) {
                emit(ResultState.Success(response.body()!!))
            }
        } catch (exception: Exception) {
            emit(ResultState.Error(exception.message ?: "Unexpected error"))
        }
    }
}