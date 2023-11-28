package com.zivame.geektrustassignment.service.data.request

import com.google.gson.annotations.SerializedName

data class FalconFindRequestBody(
    var token: String? = null,
    @SerializedName("planet_names")
    var planetNames: List<String>? = null,
    @SerializedName("vehicle_names")
    var vehicleNames: List<String>? = null
)
