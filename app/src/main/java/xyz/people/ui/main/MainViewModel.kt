package xyz.people.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import xyz.people.data.RandomUserService
import xyz.people.ui.main.entity.UserView
import xyz.people.util.ResultState

class MainViewModel @ViewModelInject constructor(
    private val randomUserService: RandomUserService
) : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()

    private var user: LiveData<ResultState<UserView>> = Transformations.switchMap(reloadTrigger) {
        liveData<ResultState<UserView>>(Dispatchers.IO) {
            emit(ResultState.Loading())
            try {
                val response = randomUserService.getUser()
                if (response.isSuccessful && response.body() != null) {
                    val userData = response.body()!!.results[0]
                    val user = UserView(
                        userData.gender,
                        "${userData.name.title} ${userData.name.first} ${userData.name.last}",
                        userData.location.country,
                        userData.location.coordinates.latitude,
                        userData.location.coordinates.longitude,
                        userData.email,
                        userData.login.uuid,
                        userData.picture.medium
                    )

                    emit(ResultState.Success(user))
                }
            } catch (exception: Exception) {
                emit(ResultState.Error(exception.message ?: "Unexpected error"))
            }
        }
    }

    init {
        retry()
    }

    fun retry() {
        reloadTrigger.value = true
    }

    fun getUser() = user

}
