package com.example.propertymanagementappwithmvm.models

data class LoginResponse(
    val appapikey: String,
    val msg: String,
    val useremail: String,
    val userid: String,
    val usertype: String
)