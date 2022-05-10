package okunev.projects.easypeasytravel.core.di_hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okunev.projects.easypeasytravel.BuildConfig
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
                .addHeader(CONTENT_TYPE_FIELD_NAME, CONTENT_TYPE)
                .addHeader(X_RAPID_API_HOST_FIELD_NAME, X_RAPID_API_HOST)
                .addHeader(X_RAPID_API_KEY_FIELD_NAME, BuildConfig.Tranlo_Api_Key)
                .build()
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun providesClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): TranslatorApi {
        return retrofit.create()
    }

    companion object {
        private const val CONTENT_TYPE_FIELD_NAME = "content-type"
        private const val CONTENT_TYPE = "application/x-www-form-urlencoded"
        private const val X_RAPID_API_HOST_FIELD_NAME = "X-RapidAPI-Host"
        private const val X_RAPID_API_HOST = "translo.p.rapidapi.com"
        private const val X_RAPID_API_KEY_FIELD_NAME = "X-RapidAPI-Key"
        private const val BASE_URL = "https://translo.p.rapidapi.com"
    }
}