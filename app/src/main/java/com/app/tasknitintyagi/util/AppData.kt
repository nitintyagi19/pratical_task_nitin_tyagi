package com.app.tasknitintyagi.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AppData(var context: Context) {

    private var prefs = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)

    private val isLogin = "isLogin"
    private val loginData = "loginData"
    private val staffData = "staffData"




    fun getPrefs(): SharedPreferences {
        return prefs
    }

  /*  fun setUserData(loginData: LoginData) {
        val json = Gson().toJson(loginData)
        val editor = prefs.edit()
        editor.putString(this.loginData, json)
        editor.apply()
    }*/

   /* fun getUserData(): LoginData? {
        var json = prefs.getString(this.loginData, "").toString()
        return if (json.isNullOrEmpty()) {
            null
        } else {
            Gson().fromJson(json, LoginData::class.java) as LoginData
        }
    }*/


    companion object {

        private var INSTANCE: AppData? = null

        @JvmStatic
        fun getInstance(context: Context?) =
            INSTANCE ?: synchronized(AppData::class.java) {
                if (context != null) {
                    INSTANCE ?: AppData(context)
                        .also { INSTANCE = it }
                } else {
                    INSTANCE
                }
            }!!

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}