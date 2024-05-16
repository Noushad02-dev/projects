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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.model.OrderDetailsDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsHistoryScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    orderDetails: SnapshotStateList<OrderDetailsDatum> = listState(key = MyDataIds.ordersDetails),
    taxableState: State<String> = stringState(key = MyDataIds.taxableState),
    taxState: State<String> = stringState(key = MyDataIds.taxState),
    discountState: State<String> = stringState(key = MyDataIds.discountState),
    totalState: State<String> = stringState(key = MyDataIds.totalState),
    storeNameState: State<String> = stringState(key = MyDataIds.storeNameState),
    orderIdState: State<String> = stringState(key = MyDataIds.orderIdState),
    routeState: State<String> = stringState(key = MyDataIds.routeState),
    orderAmountState: State<String> = stringState(key = MyDataIds.orderAmountState),
    countState: State<String> = stringState(key = MyDataIds.countState),
    dateState: State<String> = stringState(key = MyDataIds.dateState),
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
                            text = storeNameState.value,
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
                            text = routeState.value,
                            fontSize = 12.sep,
                            color = Color(0xFF8E8E8E),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = orderIdState.value,
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
                    itemsIndexed(orderDetails) { index, it ->
                        orderDetails.forEach { orderDetail ->
                            orderDetail.ordered_products.forEach { orderedProduct ->
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
                                            text = if (orderedProduct.product.name.length > 13) "${
                                                orderedProduct.product.name.take(
                                                    13
                                                )
                                            }..." else orderedProduct.product.name,
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Spacer(modifier = Modifier.height(4.dep))
                                        Text(
                                            text = "${orderedProduct.product.code} - ${orderedProduct.product.weight_string}",
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            //fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                    Text(
                                        text = "₹ ${orderedProduct.price_string} X ${orderedProduct.quantity}",
                                        fontSize = 14.sep,
                                        color = Color.Black,
                                        //fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        text = "₹ ${orderedProduct.sub_total_amount_string}",
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
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    //.padding(horizontal = 24.dep)
                    //.padding(bottom = 50.dep)
                    .fillMaxSize()
            ) {

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
                            text = "₹ ${taxableState.value}",
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
                            text = "₹ ${taxState.value}",
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
                            text = "₹ ${discountState.value}",
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
                            text = "₹ ${totalState.value}",
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