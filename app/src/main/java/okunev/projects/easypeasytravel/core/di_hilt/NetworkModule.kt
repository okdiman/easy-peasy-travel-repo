package okunev.projects.easypeasytravel.core.di_hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okunev.projects.easypeasytravel.recognizer.data.network.TranslatorApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Host", "translo.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "8b41dba7e2mshacbcf7ad0fe73b7p1dd78ejsnf92b8abd9d55")
                .build()
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun providesClient(intercaptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(intercaptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://translo.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): TranslatorApi {
        return retrofit.create()
    }
}