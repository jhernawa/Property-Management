package com.example.propertymanagementappwithmvm.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.repositories.AuthRepository

class RegisterViewModel : ViewModel(){

    var email : String? = null
    var landlordEmail : String? = null
    var password : String? = null
    var confirmPassword : String? = null
    var account : String? = null

    private var listener : RegisterViewModelInterface? = null

    interface RegisterViewModelInterface{
        fun goToLoginButton()
        fun onSuccess(registerResponse : LiveData<String>)
        fun onError(message : String)
    }

    fun setupListener(listener : RegisterViewModelInterface){
        this.listener = listener
    }

    fun onRegisterButton(view : View){

        /*landlord account does not to check for the landlord email
        We are going to register it the same as email into the api*/
        if(account == Config.LANDLORD_ACCOUNT){
            if(email.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()){
                listener?.onError("Fields cannot be empty")
                return
            }
        }
        else{
            if(email.isNullOrEmpty() || landlordEmail.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()){
                listener?.onError("Fields cannot be empty")
                return
            }
        }


        if(password != confirmPassword){
            listener?.onError("Password don't match")
            return
        }

        //register the email and landlord email the same if account is landlord
        if(account == Config.LANDLORD_ACCOUNT)
            landlordEmail = email

        var response : LiveData<String> = AuthRepository().registerUser(email!!, landlordEmail!!, password!!, account!!)
        listener?.onSuccess(response)

    }

    fun onAlreadyRegisteredButton(view : View){
        listener?.goToLoginButton()
    }



}