package com.example.propertymanagementappwithmvm.viewmodels

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.propertymanagementappwithmvm.models.LoginResponse
import com.example.propertymanagementappwithmvm.repositories.AuthRepository

class LoginViewModel : ViewModel(){

    var email : String? = null
    var password : String? = null

    private var listener : LoginViewModelInterface? = null

    interface LoginViewModelInterface{
        fun goToRegisterButton()
        fun onSuccess(loginResponse : LiveData<LoginResponse>)
        fun onError(message : String)
    }

    fun setupListener(listener : LoginViewModelInterface){
        this.listener = listener
    }

    fun onLoginButton(view : View){
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            listener?.onError("Fields cannot be empty")
            return
        }

        var loginResponse : LiveData<LoginResponse> = AuthRepository().loginUser(email!!, password!!)
        listener?.onSuccess(loginResponse)
    }

    fun onNewUserButton(view : View){
        listener?.goToRegisterButton()
    }


}