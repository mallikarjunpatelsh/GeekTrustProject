package com.zivame.geektrustassignment.di.module

import com.zivame.geektrustassignment.service.remote.FalcanoRepository
import com.zivame.geektrustassignment.service.remote.IFalcanoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsFalcanRepo(falcanoRepository: FalcanoRepository): IFalcanoRepository
}