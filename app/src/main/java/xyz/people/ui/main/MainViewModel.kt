package xyz.people.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import xyz.people.data.RandomUserService
import xyz.people.util.Loading

class MainViewModel @ViewModelInject constructor(
    private val randomUserService: RandomUserService
) : ViewModel() {

    fun getUser() = liveData(Dispatchers.IO) {
        emit(Loading)
        try {
            emit(randomUserService.getUser())
        } catch (exception: Exception) {
            emit(exception.message ?: "Unexpected error")
        }
    }
}