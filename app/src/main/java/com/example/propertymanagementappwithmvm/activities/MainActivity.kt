package com.example.propertymanagementappwithmvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.activities.property_screen.ListPropertyActivity
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if user is logged in already
        var sessionManager = SessionManager(this)
        if(!sessionManager.checkLogin()){
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        init()
    }

    private fun init(){
        button_logout.setOnClickListener(this)
        layout_alerts.setOnClickListener(this)
        layout_properties.setOnClickListener(this)
        layout_tenants.setOnClickListener(this)
        layout_transactions.setOnClickListener(this)
        layout_collect_rent.setOnClickListener(this)
        layout_to_do_list.setOnClickListener(this)
        layout_trips.setOnClickListener(this)
        layout_documents.setOnClickListener(this)
        layout_vendors.setOnClickListener(this)
    }

    override fun onClick(v : View) {
        when(v.id){
            R.id.layout_properties -> startActivity(Intent(this, ListPropertyActivity::class.java))
            R.id.button_logout -> handleButtonLogout()
        }

    }



    private fun handleButtonLogout(){

        var sessionManager = SessionManager(this)
        sessionManager.deleteUser()
        startActivity(Intent(this, LoginActivity::class.java))
    }



}
