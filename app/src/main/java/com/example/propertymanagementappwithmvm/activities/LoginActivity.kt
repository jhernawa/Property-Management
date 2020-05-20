package com.example.propertymanagementappwithmvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.databinding.ActivityLoginBinding
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import com.example.propertymanagementappwithmvm.models.LoginResponse
import com.example.propertymanagementappwithmvm.viewmodels.LoginViewModel
import com.example.propertymanagementappwithmvm.viewmodels.RegisterViewModel

class LoginActivity : AppCompatActivity(), LoginViewModel.LoginViewModelInterface {

    lateinit var loginActivityBinding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        // layout data binding
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //set the view model
        var loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        //bind registerViewModel with the layout
        loginActivityBinding.loginViewModel = loginViewModel

        //set this activity as a listener to the login view model
        loginViewModel.setupListener(this)

    }

    override fun goToRegisterButton() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onSuccess(loginResponse: LiveData<LoginResponse>) {
        loginResponse.observe(this, object : Observer<LoginResponse> {
            override fun onChanged(t: LoginResponse?) {
                Log.d("aaa", "login onSuccess")
                Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_SHORT).show()

                //save the user email and password on the shared preference
                var sessionManager = SessionManager(this@LoginActivity)
                sessionManager.saveUser(loginResponse.value!!)



                finish()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }

        })
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
