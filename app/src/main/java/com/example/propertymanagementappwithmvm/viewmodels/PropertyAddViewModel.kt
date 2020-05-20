package com.example.propertymanagementappwithmvm.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import com.example.propertymanagementappwithmvm.models.PropertyAddResponse
import com.example.propertymanagementappwithmvm.models.User
import com.example.propertymanagementappwithmvm.repositories.PropertyRepository

class PropertyAddViewModel() : ViewModel() {

    var address : String? = null
    var city : String? = null
    var state_or_province : String? = null
    var country : String? = null
    var zip_or_postal_code : String? = null
    var status : String? = null
    var multiple_units : String = "No"
    var purchase_price : String? = null
    var exclude_from_dashboard : String = "No"
    var mortgage : String = "No"

    var image_path : String = ""

    var user_id : String? = null
    var user_type : String? = null
    var latitude : String = "123.45"
    var longitude : String = "543.21"

    private var listener : PropertyViewModelInterface? = null

    interface PropertyViewModelInterface{
        fun onError(message : String)
        fun onSuccess(propertyAddReposnse : LiveData<PropertyAddResponse>)
        fun handleCheckBox(view : View)
        fun setUser()
        fun handleOpenCamera()
    }

    fun setupListener(listener : PropertyViewModelInterface){
        this.listener = listener
    }

    fun addProperty(view : View){

        Log.d("aaaa", "inside the view model")
        if(address.isNullOrEmpty() || city.isNullOrEmpty() || state_or_province.isNullOrEmpty() ||
                country.isNullOrEmpty() || status.isNullOrEmpty() || purchase_price.isNullOrEmpty() ){
            listener?.onError("Fields cannot be empty")

            return
        }


        //make the network call
        //var propertyAddResponse : LiveData<PropertyAddResponse> = PropertyRepository().addProperty(address!!, city!!, state_or_province!!, country!!, status!!, purchase_price!!, mortgage, user_id!!, user_type!!, latitude, longitue)

        listener?.setUser()


        Log.d("aaaaa", address)
        Log.d("aaaaa", city)
        Log.d("aaaaa", state_or_province)
        Log.d("aaaaa", country)
        Log.d("aaaaa", status)
        Log.d("aaaaa", purchase_price)
        Log.d("aaaaa", mortgage)
        Log.d("aaaaa", user_id)
        Log.d("aaaaa", user_type)
        Log.d("aaaaa", latitude)
        Log.d("aaaaa", longitude)
        var propertyAddResponse = PropertyRepository().addProperty(address!!, city!!, state_or_province!!, country!!, status!!, purchase_price!!, mortgage, "354", "landlord", latitude, longitude)
        listener?.onSuccess(propertyAddResponse)

    }

    fun isCheckBox(view : View){
        listener?.handleCheckBox(view)
    }

    fun onClickCamera(view : View){
        listener?.handleOpenCamera()

    }




}