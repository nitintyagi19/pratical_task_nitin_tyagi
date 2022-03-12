package com.app.tasknitintyagi.util

import android.app.Activity
import android.os.Bundle
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.ui.BaseActivity


class AppDialog {

    private var onClickListener: OnClickListener? = null
    private var currentActivity: Activity? = null
    private var bundle: Bundle = Bundle()

    constructor(currentActivity: Activity, message: String, isSingleAction: Boolean) {
        this.currentActivity = currentActivity

        bundle = Bundle()
        bundle.putString("title", currentActivity.getString(R.string.app_name))
        bundle.putString("message", message)
        bundle.putString("positiveText", currentActivity.getString(R.string.ok))
        bundle.putString("negativeText", if (isSingleAction) "" else currentActivity.getString(R.string.cancel))
        showDialogPopUp()
    }

    constructor(currentActivity: Activity, title: String, message: String, isSingleAction: Boolean) {
        this.currentActivity = currentActivity

        bundle = Bundle()
        bundle.putString("title", if (title.isEmpty()) currentActivity.getString(R.string.app_name) else title)
        bundle.putString("message", message)
        bundle.putString("positiveText", currentActivity.getString(R.string.ok))
        bundle.putString("negativeText", if (isSingleAction) "" else currentActivity.getString(R.string.cancel))
        showDialogPopUp()
    }

    constructor(currentActivity: Activity, title: String, message: String, Ok: String, cancel: String, isSingleAction: Boolean) {
        this.currentActivity = currentActivity

        bundle = Bundle()
        bundle.putString("title", if (title.isEmpty()) currentActivity.getString(R.string.app_name) else title)
        bundle.putString("message", message)
        bundle.putString("positiveText", Ok)
        bundle.putString("negativeText", if (isSingleAction) "" else cancel)
        showDialogPopUp()
    }

    fun setOnItemClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    private fun showDialogPopUp() {

        val dialog: AppDialogFragment = AppDialogFragment.getInstance(currentActivity!!, bundle)
        dialog.setOnClickListener(object : AppDialogFragment.CommonDialogNavigator {

            override fun onPositiveClick() {
                if (onClickListener == null) {
                    dialog.dismiss()
                } else {
                    dialog.dismiss()
                    onClickListener!!.onPositiveClick()
                }
            }

            override fun onNegativeClick() {
                if (onClickListener == null) {
                    dialog.dismiss()
                } else {
                    dialog.dismiss()
                    onClickListener?.onNegativeClick()
                }
            }
        })

        if (currentActivity is BaseActivity<*, *>) {
            dialog.show((currentActivity as BaseActivity<*, *>).supportFragmentManager, "common")
        }
    }

    interface OnClickListener {
        fun onPositiveClick()

        fun onNegativeClick()
    }
}