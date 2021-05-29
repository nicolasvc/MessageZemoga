package com.example.messagezemoga.transversal.retrofithelper

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    companion object {
        lateinit var retrofit: Retrofit
        var path: String = ""

        @JvmStatic
        fun getCliente(newPath: String): Retrofit {
            if (path != newPath) {
                path = newPath
                retrofit = Retrofit.Builder()
                    .baseUrl(newPath)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    }
}