package com.example.propertymanagementappwithmvm.repositories

import android.util.Log
import androidx.core.graphics.scaleMatrix
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementappwithmvm.models.LoginResponse
import com.example.propertymanagementappwithmvm.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository{

    fun registerUser(email : String, landlordEmail : String, password : String, account : String) : LiveData<String> {

        var registerResponse : MutableLiveData<String> = MutableLiveData<String>()
        var request = MyApi().registerUser(email, landlordEmail, password, account)
        request.enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.d("aaa", t!!.message)

            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                 Log.d("aaa", "register is succeed")
                 registerResponse.value = response!!.body()
            }

        })

        return registerResponse
    }

    fun loginUser(email : String, password: String) : LiveData<LoginResponse>{

        var loginResponse : MutableLiveData<LoginResponse> = MutableLiveData()

        var request = MyApi().loginUser(email, password)
        request.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Log.d("aaa", t!!.message)
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?)
            {
                Log.d("aaa", "login is succeed")
                loginResponse.value = response!!.body()
            }

        })

        return loginResponse
    }
}