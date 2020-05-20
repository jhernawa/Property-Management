package com.example.propertymanagementappwithmvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.propertymanagementappwithmvm.models.Property
import com.example.propertymanagementappwithmvm.models.PropertyDeleteResponse
import com.example.propertymanagementappwithmvm.models.PropertyGetResponse
import com.example.propertymanagementappwithmvm.repositories.PropertyRepository

class PropertyGetViewModel : ViewModel(){

    var propertiesList : ArrayList<Property> = ArrayList()

    private var listenerActivity : PropertyGetViewModelActivityInterface? = null
    private var listenerAdapter : PropertyGetViewModelAdapterInterface? = null

    interface PropertyGetViewModelActivityInterface{
        fun onSuccessAdd(propertyGetResponse : LiveData<PropertyGetResponse>)

    }

    interface PropertyGetViewModelAdapterInterface{
        fun onSuccessDelete(propertyDeleteReponse : LiveData<PropertyDeleteResponse>)
    }

    fun setupActivityListener(listener : PropertyGetViewModel.PropertyGetViewModelActivityInterface){
        this.listenerActivity = listener
    }

    fun setupAdapterListener(listener : PropertyGetViewModel.PropertyGetViewModelAdapterInterface){
        this.listenerAdapter = listener
    }




    fun getProperty(userId : String, userType : String){

        var propertyGetResponse : LiveData<PropertyGetResponse> = PropertyRepository().getProperty(userId, userType)
        listenerActivity?.onSuccessAdd(propertyGetResponse)
    }

    fun deleteProperty(propertyId : String){

        var propertyDeleteReponse : LiveData<PropertyDeleteResponse> = PropertyRepository().deleteProperty(propertyId)
        listenerAdapter?.onSuccessDelete(propertyDeleteReponse)
    }
}