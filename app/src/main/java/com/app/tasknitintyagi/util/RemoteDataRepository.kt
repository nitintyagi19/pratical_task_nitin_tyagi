package com.app.tasknitintyagi.util

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RemoteDataRepository(var app: Application){

    private fun makeOkHttpClient(): OkHttpClient {

        val tlsSocketFactory = TLSSocketFactory()
        val networkConnectionInterceptor = NetworkConnectionInterceptor(app)
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(makeLoggingInterceptor())
            .addInterceptor(networkConnectionInterceptor)
            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private val authInterceptor = Interceptor { chain ->

        val newRequest =
            chain.request()
                .newBuilder()
                .build()
        chain.proceed(newRequest)
    }
    /**
     * Get Retrofit Instance
     */
    private fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(URLHelper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(makeOkHttpClient())
            .build()
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    fun getRemoteDataSource(): RemoteDataSource {
        return getRetrofitInstance().create(RemoteDataSource::class.java!!)
    }

}