package com.example.propertymanagementappwithmvm.activities.property_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.adapters.RecyclerViewPropertiesAdapter
import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import com.example.propertymanagementappwithmvm.models.PropertyDatabase
import com.example.propertymanagementappwithmvm.models.PropertyDeleteResponse
import com.example.propertymanagementappwithmvm.models.PropertyGetResponse
import com.example.propertymanagementappwithmvm.viewmodels.PropertyGetViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_property_list.*

class ListPropertyActivity : AppCompatActivity(), PropertyGetViewModel.PropertyGetViewModelActivityInterface {

    lateinit var recyclerViewPropertiesAdapter : RecyclerViewPropertiesAdapter

    lateinit var propertyGetViewModel: PropertyGetViewModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_list)

        //view model
        propertyGetViewModel = ViewModelProviders.of(this).get(PropertyGetViewModel::class.java)
        propertyGetViewModel.setupActivityListener(this)

        //recycler view
        recyclerViewPropertiesAdapter = RecyclerViewPropertiesAdapter(this,this, propertyGetViewModel)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerViewPropertiesAdapter

        //set up adapter as the second listener
        propertyGetViewModel.setupAdapterListener(recyclerViewPropertiesAdapter)

        //session manager
        sessionManager = SessionManager(this)

        init()
    }

    private fun init() {

        var user = sessionManager.getUser()
        propertyGetViewModel.getProperty(user.userid!!, user.usertype!!)


        button_add_property.setOnClickListener{
            startActivity(Intent(this, AddPropertyActivity::class.java))
        }

    }

    override fun onSuccessAdd(propertyGetResponse: LiveData<PropertyGetResponse>) {
        propertyGetResponse.observe(this, object : Observer<PropertyGetResponse> {
            override fun onChanged(t: PropertyGetResponse?) {
                //update the recycler view
                Toast.makeText(this@ListPropertyActivity, "recycler view added", Toast.LENGTH_SHORT).show()
                recyclerViewPropertiesAdapter.setData(t!!.Property)

                //recyclerViewPropertiesAdapter.updateImage()

            }

        })
    }


}
