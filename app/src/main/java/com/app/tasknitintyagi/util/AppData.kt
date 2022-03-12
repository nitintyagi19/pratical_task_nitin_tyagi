package com.app.tasknitintyagi.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AppData(var context: Context) {

    private var prefs = context.getSharedPreferences("AppData", Context.MODE_PRIVATE)

    private val email = "email"
    private val password = "password"

    fun getPrefs(): SharedPreferences {
        return prefs
    }

    fun setEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(this.email, email)
        editor.apply()
    }

    fun getEmail(): String {
        return prefs.getString(this.email, "").toString()
    }

    fun setPassword(password: String) {
        val editor = prefs.edit()
        editor.putString(this.password, email)
        editor.apply()
    }

    fun getPassword(): String {
        return prefs.getString(this.password, "").toString()
    }


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