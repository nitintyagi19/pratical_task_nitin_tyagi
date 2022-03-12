package com.app.tasknitintyagi.util

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.app.tasknitintyagi.model.FeedListModelItem


object Utility {

    var CityList = ArrayList<FeedListModelItem>()
    var TabCurrentPosition = 0
    var isSelectionEnable = false

    fun getListFilter(key: String): ArrayList<FeedListModelItem> {
        var filterList = ArrayList<FeedListModelItem>()
        if (key == "ALL") {
            filterList.addAll(CityList)
        } else {
            for (i in CityList.indices) {
                if (CityList[i].city == key) {
                    filterList.add(CityList[i])
                }
            }
        }
        return filterList
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun hideKeyboard(view: View,context: Context) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getFilter(search: String, state: ArrayList<FeedListModelItem>): List<FeedListModelItem>? {
        val filterList: MutableList<FeedListModelItem> = ArrayList()
        return if (search.isEmpty()) {
            state
        } else {
            for (row in state) {
                if (row.first_name.toLowerCase().contains(search.toLowerCase())||
                    row.last_name.toLowerCase().contains(search.toLowerCase())||
                    row.city.toLowerCase().contains(search.toLowerCase())) {
                    filterList.add(row)
                }
            }
            filterList
        }
    }
}