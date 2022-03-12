package com.app.tasknitintyagi.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.tasknitintyagi.util.AppData
import com.app.tasknitintyagi.util.NetworkConnectionInterceptor
import com.app.tasknitintyagi.util.RemoteDataSource
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


abstract class BaseViewModel (var baseApplication: Application,var remoteDataSource: RemoteDataSource): ViewModel() {
    val showToast = MutableLiveData<String>()
    val showSnackbar = MutableLiveData<String>()
    val showProgress = MutableLiveData<Boolean>()
    val errorCode400 = MutableLiveData<Boolean>()
    val sessionExpired = MutableLiveData<Boolean>()
    lateinit var appData : AppData

    abstract fun create()
    abstract fun start()
    abstract fun stop()
    abstract fun destroy()

    fun isInternetConnected(): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager = baseApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager?.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            }
        }
        else{
            val cm = baseApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            result = activeNetwork?.isConnectedOrConnecting == true
        }

        return result
    }

    suspend inline fun <reified T> apiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()
        } else {
            val message = StringBuilder()
            val error = response.errorBody()?.string()
            error?.let {
                try {
                    if(response.code() == 400){
                        val errorMsg: String? = JSONObject(it).optString("message")
                        if (!errorMsg.isNullOrEmpty()) {
                            message.append(errorMsg)
                        } else {
                            message.append("Session Expired.")
                        }
                        //AppDialog(Activity(),errorMsg.toString(),true)
                    }
                    else if (response.code() == 401 || response.code() == 403) {
                        val errorMsg: String? = JSONObject(it).optString("message")
                        if (!errorMsg.isNullOrEmpty()) {
                            message.append(errorMsg)
                        } else {
                            message.append("Session Expired.")
                        }
                    }
                    else{
                        val errorMsg: String? = JSONObject(it).optString("message")
                        if (!errorMsg.isNullOrEmpty()) {
                            message.append(errorMsg)
                        } else {
                            message.append("Session Expired.")
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            throw NetworkConnectionInterceptor.ApiException(message.toString())
        }
    }

}