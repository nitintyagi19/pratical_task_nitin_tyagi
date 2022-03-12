package com.app.tasknitintyagi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.ActivityLoginBinding
import com.app.tasknitintyagi.db.TaskDatabase
import com.app.tasknitintyagi.util.AppData
import com.app.tasknitintyagi.util.Utility
import com.app.tasknitintyagi.util.getViewModelFactory
import com.app.tasknitintyagi.viewModel.HomeViewModel
import com.app.tasknitintyagi.viewModel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModels { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        onClick()
    }

    private fun init() {

    }

    private fun onClick() {

        mBinding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        mBinding.btnLogin.setOnClickListener {
            Utility.hideKeyboard(it,this)
            if (isValidate()) {
                callLogin()
            }
        }
    }

    private fun callLogin() {

        val email = mBinding.etEmail.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val model = TaskDatabase.getInstance(this).taskDao().getUserData(email)

        model?.let {
            if (email == it.email && password == it.password) {
                AppData.getInstance(this).setEmail(email)
                AppData.getInstance(this).setPassword(password)
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show()
            }
        } ?: kotlin.run {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidate(): Boolean {

        if (mBinding.etEmail.text.toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show()
            return false
        } else if (!Utility.isValidEmail(mBinding.etEmail.text.toString().trim())) {
            Toast.makeText(this, getString(R.string.enter_correct_email), Toast.LENGTH_SHORT).show()
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