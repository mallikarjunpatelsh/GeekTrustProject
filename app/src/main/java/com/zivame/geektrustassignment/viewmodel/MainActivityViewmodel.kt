package com.zivame.geektrustassignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivame.geektrustassignment.service.data.request.FalconFindRequestBody
import com.zivame.geektrustassignment.service.data.response.FalconeResponse
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.data.response.Vehicles
import com.zivame.geektrustassignment.service.remote.IFalcanoRepository
import com.zivame.geektrustassignment.service.remote.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewmodel @Inject constructor(private val repository: IFalcanoRepository): ViewModel() {
    private val _planets = MutableStateFlow<Resource<Planets?>>(Resource.loading(true))
    val planets = _planets.asStateFlow()
    var planetData: Planets? = null
    var vehicleData: Vehicles? = null
    var originalVehicleData: Vehicles? = null
    private val planetsSelected = arrayListOf("","","","")
    private val vehiclesSelected = arrayListOf("","","","")
    private var previousVehicleIndex: Int? = null
    private var previousPlanetIndex: Int? = null

    private val _vehicles = MutableStateFlow<Resource<Vehicles?>>(Resource.loading(true))
    val vehicles = _vehicles.asStateFlow()

    private val _result = MutableStateFlow<Resource<FalconeResponse?>>(Resource.loading(true))
    val result = _result.asStateFlow()

    private val _vehiclesDataAfterSelect = MutableStateFlow<Vehicles?>(vehicleData)
    val vehiclesDataAfterSelect = _vehiclesDataAfterSelect.asStateFlow()

    fun fetchPlanets() {
        viewModelScope.launch {
            _planets.emit(Resource.loading(true))
            repository.getPlanets().collect {
                planetData = it.data
                _planets.emit(it)
            }
        }
    }

    fun fetchVehicles() {
        viewModelScope.launch {
            _vehicles.emit(Resource.loading(true))
            repository.getVehicles().collect {
                vehicleData = it.data
                originalVehicleData = it.data
                _vehicles.emit(it)
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            repository.getToken().collect {
                it.data?.token?.let {
                    submitFalcano(it)
                }
            }
        }
    }

    fun submitFalcano(token: String) {
        viewModelScope.launch {
            val requestBody = FalconFindRequestBody(
                token = token,
                planetNames = planetsSelected,
                vehicleNames = vehiclesSelected
            )
            repository.findFalcano(requestBody).collect {
                if (it.data?.error?.isNotEmpty() == true) {
                    getToken()
                } else {
                    _result.emit(it)
                }
            }
        }
    }

    fun selectedPlanets(position: Int, planetName: String) {
        planetsSelected[position] = planetName
    }

    fun selectedVehicles(position: Int, vehicleName: String, indexOf: Int) {
        vehiclesSelected[position] = vehicleName
        vehicleData?.get(indexOf)?.let {
            it.total_no = it.total_no?.minus(1)
        }
        if (previousPlanetIndex != null && previousPlanetIndex == position) {
            previousVehicleIndex?.let {
                vehicleData?.get(it)?.let { item ->
                    item.total_no = item.total_no?.plus(1)
                }
            }
        }
        previousPlanetIndex = position
        previousVehicleIndex = indexOf


    }

}