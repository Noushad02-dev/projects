package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.vxplore.newjayadistributor.MyDataIds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsHistoryScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Order Details",
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
        }
    )
    {
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
                            text = "abc",
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
                            text = "routeState.value",
                            fontSize = 12.sep,
                            color = Color(0xFF8E8E8E),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "23434434",
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
                                text = "₹ 787}",
                                fontSize = 16.sep,
                                color = Color(0xFF575151),
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.width(8.dep))
                            Text(
                                text = "{5} Items",
                                fontSize = 12.sep,
                                color = Color(0xFF8E8E8E),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(
                            text = "12/78/90",
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
                    thickness = .8.dep,
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
                    items(count = 5) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 20.dep)
                                .fillMaxWidth()
                        ) {
                            Column(
                            ) {
                                Text(
                                    text = "product.name",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.height(4.dep))
                                Text(
                                    text = "{1111} - {5g}",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    //fontWeight = FontWeight.ExtraBold
                                )
                            }
                            Text(
                                text = "₹ 21 X 2",
                                fontSize = 14.sep,
                                color = Color.Black,
                                //fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "₹ 2332",
                                fontSize = 16.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dep))
                        Divider(
                            thickness = .8.dep,
                            color = Color(0xFFEBEBEB)
                        )
                        Spacer(modifier = Modifier.height(6.dep))
                    }
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    //.padding(horizontal = 24.dep)
                //.padding(bottom = 50.dep)
                    .fillMaxSize()
            ){

                // Spacer(modifier = Modifier.height(6.dep))
                Column(
                    modifier = Modifier
                        .background(Color(0xFFF2F2F2))
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
                            text = "Taxable Amount",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ {taxableState.value}",
                            fontSize = 14.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dep))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Taxes",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ {taxState.value}",
                            fontSize = 14.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dep))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Discount",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ {discountState.value}",
                            fontSize = 14.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dep))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Total",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ {totalState.value}",
                            fontSize = 14.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}