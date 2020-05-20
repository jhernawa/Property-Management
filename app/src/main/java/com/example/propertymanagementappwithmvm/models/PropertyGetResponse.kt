package com.example.propertymanagementappwithmvm.models

data class PropertyGetResponse(
    val Property: ArrayList<Property>
)

data class Property(
    val id: String,
    val propertyaddress: String,
    val propertycity: String,
    val propertycountry: String,
    val propertylatitude: String,
    val propertylongitude: String,
    val propertymortageinfo: String,
    val propertypurchaseprice: String,
    val propertystate: String,
    val propertystatus: String,
    val propertyuserid: String,
    val propertyusertype: String
)