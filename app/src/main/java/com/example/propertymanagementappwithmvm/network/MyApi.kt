package com.example.propertymanagementappwithmvm.network

import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.models.LoginResponse
import com.example.propertymanagementappwithmvm.models.PropertyAddResponse
import com.example.propertymanagementappwithmvm.models.PropertyDeleteResponse
import com.example.propertymanagementappwithmvm.models.PropertyGetResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.GsonBuilder
import com.google.gson.Gson



interface MyApi{


    //Authentication
    @GET("pro_mgt_reg.php?")
    fun registerUser(@Query("email") email:String,
                     @Query("landlord_email") landlord_email : String,
                     @Query("password") password : String,
                     @Query("account_for") account : String ) : Call<String>

    @GET("pro_mgt_login.php?")
    fun loginUser(@Query("email") email : String,
                  @Query("password") password : String) : Call<LoginResponse>

    //Property
    @GET("pro_mgt_add_pro.php?")
    fun addProperty(@Query("address") address : String,
                    @Query("city") city : String,
                    @Query("state") state : String,
                    @Query("country") country : String,
                    @Query("pro_status") pro_status : String,
                    @Query("purchase_price") purchase_price : String,
                    @Query("mortage_info") mortage_info : String,
                    @Query("userid") userid : String,
                    @Query("usertype") usertype : String,
                    @Query("latitude") latitude : String,
                    @Query("longitude") longitude : String) : Call<PropertyAddResponse>

    @GET("property.php?")
    fun getProperty(@Query("userid") userid: String,
                    @Query("usertype") usertype: String) : Call<PropertyGetResponse>

    @GET("remove-property.php?")
    fun deleteProperty(@Query("propertyid") propertyId : String) : Call<PropertyDeleteResponse>

    companion object{

        operator fun invoke() : MyApi{
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit
                .Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MyApi::class.java)
        }
    }

}