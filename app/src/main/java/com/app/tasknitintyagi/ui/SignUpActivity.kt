package com.app.tasknitintyagi.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.ActivitySignUpBinding
import com.app.tasknitintyagi.db.TaskDatabase
import com.app.tasknitintyagi.model.SignUpModel
import com.app.tasknitintyagi.util.AppData
import com.app.tasknitintyagi.util.Utility
import com.app.tasknitintyagi.util.getViewModelFactory
import com.app.tasknitintyagi.viewModel.HomeViewModel
import com.app.tasknitintyagi.viewModel.SignUpViewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpViewModel>() {

    var gender: String = "Select Gender"

    override val layoutId: Int
        get() = R.layout.activity_sign_up
    override val viewModel: SignUpViewModel by viewModels { getViewModelFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        onClick()
    }

    private fun init() {

        mBinding.genderSpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    (view as TextView).setTextColor(Color.parseColor("#707070"))
                }
                gender = parent!!.getItemAtPosition(position).toString()
            }
        }
    }

    private fun onClick() {

        mBinding.btnSignup.setOnClickListener {
            Utility.hideKeyboard(it, this)
            if (isValidate()) {
                callSignUp()
            }
        }
    }

    private fun callSignUp() {

        val fullName = mBinding.etFullName.text.toString().trim()
        val email = mBinding.etEmail.text.toString().trim()
        val mobile = mBinding.etMobileNumber.text.toString().trim()
        val city = mBinding.etCity.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val model = SignUpModel(fullName, email, mobile, gender, city, password)
        AppData.getInstance(this).setEmail(email)
        AppData.getInstance(this).setPassword(password)
        // save model into database
        TaskDatabase.getInstance(this).taskDao().insert(model)
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        startActivity(intent)
    }

    private fun isValidate(): Boolean {

        if (mBinding.etFullName.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_full_name), Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (mBinding.etEmail.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show()
            return false
        } else if (!Utility.isValidEmail(mBinding.etEmail.text.toString().trim())) {
            Toast.makeText(this, getString(R.string.enter_correct_email), Toast.LENGTH_SHORT).show()
            return false
        } else if (mBinding.etMobileNumber.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_mobile_number), Toast.LENGTH_SHORT).show()
            return false
        } else if (mBinding.etMobileNumber.text.toString().trim().length < 10) {
            Toast.makeText(
                this,
                getString(R.string.length_should_be_ton),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (mBinding.etCity.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_city), Toast.LENGTH_LONG).show()
            return false
        } else if (gender == getString(R.string.select_gender)) {
            Toast.makeText(this, getString(R.string.please_select_gender), Toast.LENGTH_LONG).show()
            return false
        } else if (mBinding.etPassword.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show()
            return false
        } else if (mBinding.etPassword.text.toString().trim().length < 6) {
            Toast.makeText(
                this,
                getString(R.string.password_length),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

}