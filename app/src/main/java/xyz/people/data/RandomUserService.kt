package xyz.people.data

import retrofit2.Response
import retrofit2.http.GET
import xyz.people.data.entity.User

interface RandomUserService {
    @GET("api")
    suspend fun getUser(): Response<User>
}