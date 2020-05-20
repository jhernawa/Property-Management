package com.example.propertymanagementappwithmvm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.adapters.ViewPagerRegisterAdapter
import com.example.propertymanagementappwithmvm.fragments.LandlordFragment
import com.example.propertymanagementappwithmvm.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //set up the fragment adapter
        var myFragmentAdapter = ViewPagerRegisterAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        view_pager.adapter = myFragmentAdapter



    }




}
