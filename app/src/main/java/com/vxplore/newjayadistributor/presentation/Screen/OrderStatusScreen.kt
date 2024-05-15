package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.model.OrderStatusDatum
import com.vxplore.newjayadistributor.model.TrackOrderDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderStatusScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    orderStatus: SnapshotStateList<OrderStatusDatum> = listState(key = MyDataIds.orderStatus),
    orderIdState: State<String> = stringState(key = MyDataIds.orderIdState),
    orderAmountState: State<String> = stringState(key = MyDataIds.orderAmountState),
    countState: State<String> = stringState(key = MyDataIds.countState),
    dateState: State<String> = stringState(key = MyDataIds.dateState),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Order Status",
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
                    .padding(horizontal = 20.dep)
            ) {
                Spacer(modifier = Modifier.height(20.dep))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            2.dep,
                            RoundedCornerShape(4.dep),
                            clip = true,
                            DefaultShadowColor
                        )
                        .clip(RoundedCornerShape(4.dep)),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dep,
                        focusedElevation = 10.dep,
                    ),
                    shape = RoundedCornerShape(4.dep),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 54.dep),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color(0xFFDEDEDE))
                                .padding(top = 8.dep, bottom = 16.dep),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "My Order",
                                    fontSize = 18.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Column {
                                    Text(
                                        text = "Order # :",
                                        fontSize = 14.sep,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = orderIdState.value,
                                        fontSize = 14.sep,
                                        color = Color.Black,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dep))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "₹ ${orderAmountState.value}",
                                        fontSize = 16.sep,
                                        color = Color(0xFF575151),
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Spacer(modifier = Modifier.width(8.dep))
                                    Text(
                                        text = "${countState.value} Items",
                                        fontSize = 12.sep,
                                        color = Color(0xFF8E8E8E),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = dateState.value,
                                    fontSize = 12.sep,
                                    color = Color(0xFF8E8E8E),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dep))
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 10.dep),
                            verticalArrangement = Arrangement.spacedBy(20.dep)
                        ) {
                            items(orderStatus) {
                                orderStatus.forEach { orderStatus ->
                                    orderStatus.track_order.forEach { track_order ->
                                        Column(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dep)
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(
                                                            if (track_order.status) {
                                                                Color(0xFF699E73)
                                                            } else {
                                                                Color(0xFF898989)
                                                            }, CircleShape
                                                        )
                                                        .size(20.dep)
                                                )
                                                Spacer(modifier = Modifier.width(12.dep))
                                                Text(
                                                    text = track_order.name,
                                                    fontSize = 14.sep,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(
                                                            Color.White, CircleShape
                                                        )
                                                        .size(20.dep)
                                                )
                                                Spacer(modifier = Modifier.width(12.dep))
                                                Text(
                                                    text = track_order.datetime.toString(),
                                                    fontSize = 10.sep,
                                                    color = Color(0xFF8E8E8E),
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(16.dep))
                                    }
                                }
                            }
                        }
                        /*  Column (
                        modifier = Modifier
                            .padding(horizontal = 16.dep)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFF699E73), CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "Order Confirmed",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "7th May 2024",
                                fontSize = 10.sep,
                                color = Color(0xFF8E8E8E),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dep))
                    Column (
                        modifier = Modifier
                            .padding(horizontal = 16.dep)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFF699E73), CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "Loading Completed",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "7th May 2024",
                                fontSize = 10.sep,
                                color = Color(0xFF8E8E8E),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dep))
                    Column (
                        modifier = Modifier
                            .padding(horizontal = 16.dep)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFF898989), CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "Dispatched",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "-",
                                fontSize = 10.sep,
                                color = Color(0xFF8E8E8E),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dep))
                    Column (
                        modifier = Modifier
                            .padding(horizontal = 16.dep)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFF898989), CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "Delivery",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color.White, CircleShape
                                    )
                                    .size(20.dep)
                            )
                            Spacer(modifier = Modifier.width(12.dep))
                            Text(
                                text = "Expected Delivery Date 14th May 2024",
                                fontSize = 10.sep,
                                color = Color(0xFF8E8E8E),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }*/
                    }
                }
            }
        }
    }
}