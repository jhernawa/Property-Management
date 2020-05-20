package com.example.propertymanagementappwithmvm.models

data class PropertyDatabase(
    var key : String? = null,
    var address : String? = null,
    var city : String? = null,
    var state_or_province : String? = null,
    var country : String? = null,
    var zip_or_postal_code : String? = null,
    var status : String? = null,
    var multiple_units : String? = null,
    var purchase_price : String? = null,
    var exclude_from_dashboard : String? = null,
    var mortgage : String? = null,
    var image_path : String? = null,
    var user_id : String? = null,
    var user_type : String? = null,
    var latitude : String? = null,
    var longitude : String? = null
)