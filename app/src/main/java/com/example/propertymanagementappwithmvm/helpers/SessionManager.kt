package com.example.propertymanagementappwithmvm.helpers

import android.content.Context
import android.content.SharedPreferences
import com.example.propertymanagementappwithmvm.models.LoginResponse
import com.example.propertymanagementappwithmvm.models.User

class SessionManager(var mContext : Context){

    companion object{
        private const val FILE_NAME = "loggedin_user"
        private const val KEY_APP_API_KEY = "appapikey"
        private const val KEY_EMAIL = "email"
        private const val KEY_USER_ID = "id"
        private const val KEY_USER_TYPE = "type"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }
    var sharedPreferences : SharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor : SharedPreferences.Editor = sharedPreferences.edit()

    fun saveUser(loginResponse: LoginResponse){
        editor.putString(KEY_APP_API_KEY, loginResponse.appapikey)
        editor.putString(KEY_EMAIL, loginResponse.useremail)
        editor.putString(KEY_USER_ID, loginResponse.userid)
        editor.putString(KEY_USER_TYPE, loginResponse.usertype)

        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.commit()
    }

    fun getUser() : User{
        var appapikey = sharedPreferences.getString(KEY_APP_API_KEY, null)
        var email = sharedPreferences.getString(KEY_EMAIL,null)
        var id = sharedPreferences.getString(KEY_USER_ID, null)
        var type = sharedPreferences.getString(KEY_USER_TYPE, null)

        var user = User(appapikey, email, id, type)

        return user
    }
    fun checkLogin() : Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun deleteUser(){
        editor.clear()
        editor.commit()
    }

}