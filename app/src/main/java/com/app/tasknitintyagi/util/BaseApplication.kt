package com.app.tasknitintyagi.util

import android.app.Application
import androidx.multidex.MultiDexApplication



class BaseApplication : MultiDexApplication(){

    val remoteDataSource: RemoteDataSource
        get() = RemoteDataRepository(this).getRemoteDataSource()

    val getApplication: Application
        get() = this

    override fun onCreate() {
        super.onCreate()
    }
}