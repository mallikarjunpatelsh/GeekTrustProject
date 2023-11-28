package com.zivame.geektrustassignment.service.data.response

import com.google.gson.annotations.SerializedName

data class FalconeResponse(
    @SerializedName("planet_name")
    var planetName: String? = null,
    var status: String? = null,
    var error: String? = null,
)
