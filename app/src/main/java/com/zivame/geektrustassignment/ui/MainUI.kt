package com.zivame.geektrustassignment.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zivame.geektrustassignment.R
import com.zivame.geektrustassignment.service.data.response.Planets
import com.zivame.geektrustassignment.service.remote.base.Resource
import com.zivame.geektrustassignment.viewmodel.MainActivityViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MainUI() {
    var isPlanetsExpanded by remember {
        mutableStateOf(false)
    }
    var isVehiclesExpanded by remember {
        mutableStateOf(false)
    }
    val viewmodel: MainActivityViewmodel = hiltViewModel()
    val planets by viewmodel.planets.collectAsStateWithLifecycle()
    val vehicles by viewmodel.vehicles.collectAsStateWithLifecycle()
    val result by viewmodel.result.collectAsStateWithLifecycle()
    val planetData = planets.data
    val vehiclesData = vehicles.data
    var selectedPlanet by remember {
        mutableStateOf("")
    }

    var selectedVehicle by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        viewmodel.fetchPlanets()
        viewmodel.fetchVehicles()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(id = R.string.falcano_finding))
                }
            )
        },
    ) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxHeight()
        ) {
            when (planets.status) {
                Resource.Status.LOADING -> {
                    Text(
                        text = stringResource(id = R.string.loading),
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                    )
                }

                Resource.Status.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                    ) {
                        item {
                            for (i in 0 until 4) {
                                Text(
                                    text = "Destination ${i+1}",
                                    textAlign = TextAlign.Center
                                )
                                if (planetData != null) {
                                    PlanetDropDown(viewmodel = viewmodel, position = i, planets = planetData)
                                }

                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp))
                            }

                            ElevatedButton(onClick = {
                                viewmodel.getToken()
                            }) {
                                Text(text = stringResource(id = R.string.find_falcano))
                            }

                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp))

                            Text(text = "Result : ${if (result.data?.planetName?.isNotEmpty() == true) "Found planet ${result.data?.planetName}" else "Didn't find planet"}")

                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp))


                            ElevatedButton(onClick = {
                                viewmodel.fetchPlanets()
                                viewmodel.fetchVehicles()
                            }) {
                                Text(text = "Reset")
                            }

                            Spacer(modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp))
                        }
                    }
                }

                else -> {

                }
            }
        }

    }

    

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDropDown(
    viewmodel: MainActivityViewmodel,
    position: Int,
    planets: Planets
    ) {
    var isPlanetsExpanded by remember {
        mutableStateOf(false)
    }
    var selectedPlanet by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isPlanetsExpanded,
        onExpandedChange = {
            isPlanetsExpanded = !isPlanetsExpanded
        }
    ) {
        // text field
        TextField(
            value = selectedPlanet,
            onValueChange = {

            },
            readOnly = true,
            label = { Text(text = "Select the planet") },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isPlanetsExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // menu
        ExposedDropdownMenu(
            expanded = isPlanetsExpanded,
            onDismissRequest = { isPlanetsExpanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            planets.forEach { planet ->
                // menu item
                DropdownMenuItem(
                    text = {
                        Text(text = planet.name ?: "")
                    }, onClick = {
                        selectedPlanet = planet.name ?: ""
                        viewmodel.selectedPlanets(position, selectedPlanet)
                        isPlanetsExpanded = false
                    }
                )
            }
        }
    }

    if (selectedPlanet.isNotEmpty()) {
        VehiclesRadioButtonComponent(viewmodel = viewmodel, position = position)
    }

}


@Composable
fun VehiclesRadioButtonComponent(viewmodel: MainActivityViewmodel, position: Int) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            val vehicles = viewmodel.vehicleData
            Log.d("vehicle data in component: ", vehicles.toString())
            vehicles?.forEach { vehiclesItem ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (vehiclesItem.name == selectedOption),
                            onClick = {
                                if ((vehiclesItem.total_no ?: 0) > 0) {
                                    viewmodel.selectedVehicles(
                                        position,
                                        vehiclesItem.name ?: "",
                                        vehicles.indexOf(vehiclesItem)
                                    )
                                    onOptionSelected(vehiclesItem.name ?: "")
                                }
                            }
                        )
                        .align(CenterHorizontally),
                    verticalAlignment = CenterVertically,
                ) {
                    RadioButton(
                        selected = (vehiclesItem.name == selectedOption),
                        onClick = {
                            if ((vehiclesItem.total_no ?: 0) > 0) {
                                viewmodel.selectedVehicles(
                                    position,
                                    vehiclesItem.name ?: "",
                                    vehicles.indexOf(vehiclesItem)
                                )
                                onOptionSelected(vehiclesItem.name ?: "")
                            }
                        }
                    )

                    Text(
                        text = "${vehiclesItem.name} (${vehiclesItem.total_no})",
                        modifier = Modifier.padding(start = 8.dp),
                        color = if ((vehiclesItem.total_no ?: 0) > 0) Color.Black else Color.LightGray
                    )
                }
            }
        }
    }
}