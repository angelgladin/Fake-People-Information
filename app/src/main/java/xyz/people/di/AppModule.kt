package xyz.people.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.people.data.RandomUserService
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRandomUserService(retrofit: Retrofit): RandomUserService =
        retrofit.create(RandomUserService::class.java)

}