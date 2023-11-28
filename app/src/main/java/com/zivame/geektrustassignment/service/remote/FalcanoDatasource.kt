package com.zivame.geektrustassignment.service.remote

import android.content.Context
import com.zivame.geektrustassignment.service.data.request.FalconFindRequestBody
import com.zivame.geektrustassignment.service.data.response.FalconeResponse
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.data.response.Token
import com.zivame.geektrustassignment.service.data.response.Vehicles
import com.zivame.geektrustassignment.service.remote.base.BaseDataSource
import com.zivame.geektrustassignment.service.remote.base.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FalcanoDatasource @Inject constructor(@ApplicationContext private val context: Context,
                                            private val falcanoAPIService: IFalcanoAPIService)
    : IFalcanoDatasource, BaseDataSource(context) {
    override fun getPlanets(): Flow<Resource<Planets>> = getResult{
        falcanoAPIService.getPlanets()
    }

    override fun getVehicles(): Flow<Resource<Vehicles>> = getResult{
        falcanoAPIService.getVehicles()
    }

    override fun getToken(): Flow<Resource<Token>> = getResult{
        falcanoAPIService.getToken()
    }

    override fun findFalcano(requestBody: FalconFindRequestBody): Flow<Resource<FalconeResponse>> = getResult{
        falcanoAPIService.findFalacon(requestBody)
    }

}