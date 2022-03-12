package com.app.tasknitintyagi.viewModel

import android.app.Application
import androidx.lifecycle.liveData
import com.app.tasknitintyagi.util.RemoteDataSource
import com.app.tasknitintyagi.util.Resource
import com.app.tasknitintyagi.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers

class HomeViewModel(baseApplication: Application, remoteDataSource: RemoteDataSource) :
    BaseViewModel(baseApplication, remoteDataSource) {
    override fun create() {

    }

    override fun start() {

    }

    override fun stop() {

    }

    override fun destroy() {

    }

    fun feedListCall() = liveData(
        Dispatchers.Main) {
        showProgress.value = true
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiRequest { remoteDataSource.feedList()}))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}