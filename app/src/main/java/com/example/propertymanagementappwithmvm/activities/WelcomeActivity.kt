package com.example.propertymanagementappwithmvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //check if user is logged in already
        var sessionManager = SessionManager(this)
        if(sessionManager.checkLogin()){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }


        init()
    }

    private fun init(){
        button_login.setOnClickListener(this)
        button_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.button_signup -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}
