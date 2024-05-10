package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.vxplore.newjayadistributor.MyDataIds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDeliveryDetailsScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
) {
    var showBottomSheetState by remember { mutableStateOf(false) }
    var isBottomSheetLaidOut by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Due Delivery",
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
                actions = {
                    IconButton(
                        onClick = {
                            notifier.notify(
                                MyDataIds.add
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors(Color(0xFF699E73)),
                        modifier = Modifier

                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "ArrowBackIosNew",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB56)
                )
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFF8F8F8))
                    .padding(top = 8.dep, bottom = 16.dep),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Bikrimart",
                        fontSize = 16.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Order # :",
                        fontSize = 14.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Bally to Belur",
                        fontSize = 12.sep,
                        color = Color(0xFF8E8E8E),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "06909919",
                        fontSize = 14.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹ 15404",
                            fontSize = 16.sep,
                            color = Color(0xFF575151),
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(8.dep))
                        Text(
                            text = "5 Items",
                            fontSize = 12.sep,
                            color = Color(0xFF8E8E8E),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "May 8, 2024",
                        fontSize = 12.sep,
                        color = Color(0xFF8E8E8E),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dep))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 20.dep)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Item(s)",
                    fontSize = 14.sep,
                    color = Color.Black,
                    //fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Price",
                    fontSize = 14.sep,
                    color = Color.Black,
                    //fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(6.dep))
            Divider(
                thickness = .5.dep,
                color = Color(0xFFEBEBEB)
            )
            Spacer(modifier = Modifier.height(6.dep))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dep),
                contentPadding = PaddingValues(bottom = 10.dep),
                verticalArrangement = Arrangement.spacedBy(6.dep)
            ) {
                items(count = 40) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxWidth()
                    ){
                        Column(
                        ) {
                            Text(
                                text = "Butter D-Lite",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(4.dep))
                            Text(
                                text = "10016 - 300gm",
                                fontSize = 14.sep,
                                color = Color.Black,
                                //fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Text(
                            text = "₹ 32 X 10",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "₹ 320",
                            fontSize = 16.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dep))
                    Divider(
                        thickness = .5.dep,
                        color = Color(0xFFEBEBEB)
                    )
                }
            }
        }
    }
}

