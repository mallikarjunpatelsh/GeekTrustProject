package com.zivame.geektrustassignment.service.remote

import com.zivame.geektrustassignment.constant.AppConstants
import com.zivame.geektrustassignment.service.data.request.FalconFindRequestBody
import com.zivame.geektrustassignment.service.data.response.FalconeResponse
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.data.response.Token
import com.zivame.geektrustassignment.service.data.response.Vehicles
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IFalcanoAPIService {
    @GET(AppConstants.URLConstants.PLANETS)
    suspend fun getPlanets(): Response<Planets>

    @GET(AppConstants.URLConstants.VEHCILES)
    suspend fun getVehicles(): Response<Vehicles>

    @Headers(
        "Accept: application/json"
    )
    @POST(AppConstants.URLConstants.TOKEN)
    suspend fun getToken(@Body body: Any = Object()): Response<Token>

    @Headers(
        "Accept: application/json"
    )
    @POST(AppConstants.URLConstants.FIND)
    suspend fun findFalacon(@Body requestBody: FalconFindRequestBody): Response<FalconeResponse>
}