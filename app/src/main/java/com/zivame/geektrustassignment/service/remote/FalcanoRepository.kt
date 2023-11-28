package com.zivame.geektrustassignment.service.remote

import com.zivame.geektrustassignment.di.qualifier.RemoteDataSource
import com.zivame.geektrustassignment.service.data.request.FalconFindRequestBody
import com.zivame.geektrustassignment.service.data.response.FalconeResponse
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.data.response.Token
import com.zivame.geektrustassignment.service.data.response.Vehicles
import com.zivame.geektrustassignment.service.remote.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FalcanoRepository @Inject constructor(@RemoteDataSource private val falcanoDatasource: IFalcanoDatasource): IFalcanoRepository {
    override fun getPlanets(): Flow<Resource<Planets>> = flow{
        falcanoDatasource.getPlanets().collect {
            emit(it)
        }
    }

    override fun getVehicles(): Flow<Resource<Vehicles>> = flow{
        falcanoDatasource.getVehicles().collect {
            emit(it)
        }
    }

    override fun getToken(): Flow<Resource<Token>> = flow{
        falcanoDatasource.getToken().collect {
            emit(it)
        }
    }

    override fun findFalcano(requestBody: FalconFindRequestBody): Flow<Resource<FalconeResponse>> = flow{
        falcanoDatasource.findFalcano(requestBody).collect {
            emit(it)
        }
    }
}