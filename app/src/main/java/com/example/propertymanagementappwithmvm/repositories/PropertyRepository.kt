package com.example.propertymanagementappwithmvm.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementappwithmvm.models.PropertyAddResponse
import com.example.propertymanagementappwithmvm.models.PropertyDeleteResponse
import com.example.propertymanagementappwithmvm.models.PropertyGetResponse
import com.example.propertymanagementappwithmvm.network.MyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.KeyStore

class PropertyRepository {

    fun addProperty(address : String,
                    city : String,
                    state : String,
                    country : String,
                    pro_status : String,
                    purchase_price : String,
                    mortage_info : String,
                    userid : String,
                    usertype : String,
                    latitude : String,
                    longitude : String) : LiveData<PropertyAddResponse> {


        var propertyAddResponse : MutableLiveData<PropertyAddResponse> = MutableLiveData()
        var request = MyApi().addProperty(address, city, state, country, pro_status, purchase_price, mortage_info, userid, usertype, latitude, longitude)
        request.enqueue(object: Callback<PropertyAddResponse> {
            override fun onFailure(call: Call<PropertyAddResponse>?, t: Throwable?) {
                Log.d("aaaa", "failed add property")
            }

            override fun onResponse(call: Call<PropertyAddResponse>?, response: Response<PropertyAddResponse>?) {
                Log.d("aaaa", "success add property")
                propertyAddResponse.value = response?.body() //body contains the JSON that has the same format with the PropertyAddResponse
            }


        })

        return propertyAddResponse

    }

    fun getProperty(userId: String, userType: String) : LiveData<PropertyGetResponse>{

        var propertyGetResponse : MutableLiveData<PropertyGetResponse> = MutableLiveData()

        var request = MyApi().getProperty(userId, userType)
        request.enqueue(object : Callback<PropertyGetResponse>{
            override fun onFailure(call: Call<PropertyGetResponse>?, t: Throwable?) {
                Log.d("aaaa", "failed get property")
            }

            override fun onResponse(call: Call<PropertyGetResponse>?, response: Response<PropertyGetResponse>?) {
                Log.d("aaaa", "success get property")
                propertyGetResponse.value = response?.body()

            }

        })

        return propertyGetResponse

    }

    fun deleteProperty(propertyId : String) : LiveData<PropertyDeleteResponse>{

        var propertyDeleteResponse : MutableLiveData<PropertyDeleteResponse> = MutableLiveData()
        var request = MyApi().deleteProperty(propertyId)
        request.enqueue(object : Callback<PropertyDeleteResponse>{
            override fun onFailure(call: Call<PropertyDeleteResponse>?, t: Throwable?) {
                Log.d("aaaa", "failed delete property")
            }

            override fun onResponse(call: Call<PropertyDeleteResponse>?, response: Response<PropertyDeleteResponse>?) {
                Log.d("aaaa", "success get property")
                propertyDeleteResponse.value = response?.body()
            }

        })

        return propertyDeleteResponse
    }
}