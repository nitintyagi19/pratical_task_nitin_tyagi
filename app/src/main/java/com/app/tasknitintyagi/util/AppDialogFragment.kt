package com.app.tasknitintyagi.util

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.LayoutDialogBinding


class AppDialogFragment(private val mContext: Activity) : DialogFragment() {

    private var commonDialogNavigator: CommonDialogNavigator? = null
    private lateinit var mBinding: LayoutDialogBinding

    companion object {
        fun getInstance(mContext: Activity, bundle: Bundle): AppDialogFragment {
            val commonDialog = AppDialogFragment(mContext)
            val args = Bundle()
            args.putString("title", bundle.getString("title"))
            args.putString("message", bundle.getString("message"))
            args.putString("positiveText", bundle.getString("positiveText"))
            args.putString("negativeText", bundle.getString("negativeText"))
            commonDialog.arguments = args
            return commonDialog
        }
    }

    fun setOnClickListener(commonDialogNavigator: CommonDialogNavigator) {
        this.commonDialogNavigator = commonDialogNavigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_dialog, null, false)

        if (arguments != null) {
            mBinding.textHeading.text = arguments?.getString("title")
            mBinding.textMessage.text = arguments?.getString("message")
            mBinding.textPositive.text = arguments?.getString("positiveText")
            mBinding.textNegative.text = arguments?.getString("negativeText")
            if(arguments?.getString("negativeText").isNullOrEmpty()){
                mBinding.textNegative.visibility = View.GONE
            }
            else{
                mBinding.textNegative.visibility = View.VISIBLE
            }

            mBinding.textNegative.setOnClickListener {
                commonDialogNavigator?.onNegativeClick()
            }

            mBinding.textPositive.setOnClickListener {
                commonDialogNavigator?.onPositiveClick()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        val window = dialog?.window!!
        window.setBackgroundDrawableResource(R.color.colorTransparent)

        val params = window.attributes
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT

        val displayMetrics = DisplayMetrics()
        mContext.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = (displayMetrics.widthPixels * 0.8).toInt()
        window.setLayout(width, params.height)
    }

    interface CommonDialogNavigator {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}