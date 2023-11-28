package com.zivame.geektrustassignment.di.module

import com.zivame.geektrustassignment.di.qualifier.RemoteDataSource
import com.zivame.geektrustassignment.service.remote.FalcanoDatasource
import com.zivame.geektrustassignment.service.remote.IFalcanoDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DatasourceModule {

    @Binds
    @Singleton
    @RemoteDataSource
    fun bindsFalcanoDataSource(falcanoDatasource: FalcanoDatasource): IFalcanoDatasource
}