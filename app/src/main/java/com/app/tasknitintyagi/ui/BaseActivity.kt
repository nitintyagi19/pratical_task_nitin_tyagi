package com.app.tasknitintyagi.ui

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.tasknitintyagi.util.AppData
import com.app.tasknitintyagi.viewModel.BaseViewModel


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(){

    private lateinit var mProgressDialog: Dialog
    val isInternetConnected: Boolean get() = mViewModel.isInternetConnected()
    lateinit var mBinding: T
    lateinit var mViewModel: V
    @get:LayoutRes
    abstract val layoutId: Int
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        mBinding = DataBindingUtil.setContentView(this, layoutId)
       // mBinding.setVariable(BR, mViewModel)

        mViewModel.appData = AppData.getInstance(this)

        mProgressDialog = Dialog(this)
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //mProgressDialog.setContentView(R.layout.layout_progress_dialog)
        mProgressDialog.setCancelable(false)
        mProgressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mViewModel.showToast.observe(this, {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.showSnackbar.observe(this, {
            it?.let {
                //showSnackBarMsg(mBinding.root, it)
            }
        })

        mViewModel.showProgress.observe(this, {
            it?.let {
                showProgress(it)
            }
        })


        mViewModel.sessionExpired.observe(this, {
            it?.let {
                //showSessionExpired()
            }
        })

        resetTitle()
        mViewModel.create()

       // setupSocialLogin()

    }

    override fun onStart() {
        mViewModel.start()
        super.onStart()
    }

    override fun onStop() {
        mViewModel.stop()
        super.onStop()
    }

    override fun onDestroy() {
        mViewModel.destroy()
        super.onDestroy()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    fun resetTitle() {
        try {
            var label = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA).labelRes
            if (label != 0) {
                setTitle(label)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun showProgress(state: Boolean) {
        if (state) {
            if(mProgressDialog != null){
                if(!mProgressDialog.isShowing)
                    mProgressDialog.show()
            }
        } else {
            if(mProgressDialog != null){
                if(mProgressDialog.isShowing)
                    mProgressDialog.dismiss()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                else{
                    //baseViewModel.snackBarMessage.value = "App Permissions Are Not Granted"
                }
            }

            else -> {

            }
        }
    }
}