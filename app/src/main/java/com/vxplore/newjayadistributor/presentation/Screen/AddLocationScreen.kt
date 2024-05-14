package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R
import com.vxplore.newjayadistributor.model.LocData
import com.vxplore.newjayadistributor.model.LocationDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    addLocationList: SnapshotStateList<LocData> = listState(key = MyDataIds.addLocationList),
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Delivery Points",
                        fontSize = 20.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            notifier.notify(
                                MyDataIds.back
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "ArrowBackIosNew",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB56)
                )
            )
        },
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dep)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dep))
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dep)
                    .fillMaxWidth()
            ) {
                LocationSearchField()
            }
            if (loadingState.value) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = Color(0XFFFF4155),
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(20.dep))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 10.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    items(addLocationList) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "Location"
                            )
                            Spacer(modifier = Modifier.width(16.dep))
                            Text(
                                text = "${it.name} - ${it.state} - ${it.pincode}",
                                fontSize = 16.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dep))
                        Divider()
                        Spacer(modifier = Modifier.height(12.dep))
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                /*.padding(horizontal = 24.dep)
            .padding(bottom = 60.dep)*/
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    notifier.notify(MyDataIds.addLocation)
                },
                modifier = Modifier
                    .height(50.dep)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF699E73)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dep,
                    pressedElevation = 10.dep
                ),
                shape = RectangleShape
            ) {
                /*  *//*  if (loading.value) {
                          CircularProgressIndicator(
                               color = Color(0xFF699E73),
                          )
                      } else {*/
                Text(
                    "Add Location",
                    fontSize = 18.sep,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun LocationSearchField(
    notifier: NotificationService = rememberNotifier(),
    locationSearch: State<String> = stringState(key = MyDataIds.locationSearch)
) {
    OutlinedTextField(
        value = locationSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.locationSearch, it)
        },
        placeholder = {
            Text(
                text = "Search Product",
                color = Color(0XFF898989),
                fontSize = 16.sep
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        trailingIcon = {
            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color(0XFF898989)
                )
            }
        }, modifier = Modifier
            .fillMaxWidth()
    )
}