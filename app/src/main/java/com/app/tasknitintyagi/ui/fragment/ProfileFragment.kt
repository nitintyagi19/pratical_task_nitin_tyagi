package com.app.tasknitintyagi.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.tasknitintyagi.R
import com.app.tasknitintyagi.databinding.FeedsFragmentLayoutBinding
import com.app.tasknitintyagi.databinding.UserProfileFragmentLayoutBinding
import com.app.tasknitintyagi.db.TaskDatabase
import com.app.tasknitintyagi.model.SignUpModel
import com.app.tasknitintyagi.util.AppData
import com.app.tasknitintyagi.util.Utility

class ProfileFragment : Fragment() {

    lateinit var binding: UserProfileFragmentLayoutBinding
    var gender: String = "Select Gender"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.user_profile_fragment_layout,
                container,
                false
            )
        init()
        onClick()
        return binding.root
    }

    private fun init() {

        val email = AppData.getInstance(this.requireContext()).getEmail()
        val password = AppData.getInstance(this.requireContext()).getPassword()
        val model = TaskDatabase.getInstance(this.requireContext()).taskDao().getUserData(email)

        val gArray = resources.getStringArray(R.array.gender_array)

        model?.let {
            binding.etFullName.setText(it.fullName)
            binding.etEmail.setText(it.email)
            binding.etMobileNumber.setText(it.mobile)
            binding.etCity.setText(it.city)
            binding.etPassword.setText(it.password)
            gender = it.gender
            for (pos in gArray.indices) {
                if (gArray[pos] == gender) {
                    binding.genderSpn.setSelection(pos)
                }
            }
        }
    }

    private fun onClick() {
        binding.btnUpdate.setOnClickListener {
            Utility.hideKeyboard(it,this.requireContext())
            if (isValidate()) {
                val fullName = binding.etFullName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val mobile = binding.etMobileNumber.text.toString().trim()
                val city = binding.etCity.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                val model = SignUpModel(fullName, email, mobile, gender, city, password)
                Log.e("model", "$model")
                // save model into database
                 TaskDatabase.getInstance(this.requireContext()).taskDao().insert(model)
                Toast.makeText(this.requireContext(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidate(): Boolean {

        if (binding.etFullName.text.toString().trim().isEmpty()) {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.please_enter_full_name),
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        } else if (binding.etMobileNumber.text.toString().trim().isEmpty()) {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.enter_mobile_number),
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else if (binding.etMobileNumber.text.toString().trim().length < 10) {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.length_should_be_ton),
                Toast.LENGTH_LONG
            ).show()
            return false
        } else if (binding.etCity.text.toString().trim().isEmpty()) {
            Toast.makeText(this.requireContext(), getString(R.string.enter_city), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (gender == getString(R.string.select_gender)) {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.please_select_gender),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

}