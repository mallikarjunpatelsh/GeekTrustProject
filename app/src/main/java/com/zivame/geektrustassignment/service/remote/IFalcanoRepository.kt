package com.zivame.geektrustassignment.service.remote

import com.zivame.geektrustassignment.service.data.request.FalconFindRequestBody
import com.zivame.geektrustassignment.service.data.response.FalconeResponse
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.data.response.Token
import com.zivame.geektrustassignment.service.data.response.Vehicles
import com.zivame.geektrustassignment.service.remote.base.Resource
import kotlinx.coroutines.flow.Flow

interface IFalcanoRepository {
    fun getPlanets(): Flow<Resource<Planets>>
    fun getVehicles(): Flow<Resource<Vehicles>>
    fun getToken(): Flow<Resource<Token>>
    fun findFalcano(requestBody: FalconFindRequestBody): Flow<Resource<FalconeResponse>>
}