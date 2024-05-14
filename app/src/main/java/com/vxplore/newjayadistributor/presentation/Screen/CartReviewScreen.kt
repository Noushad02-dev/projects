package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R
import com.vxplore.newjayadistributor.model.CartProduct
import com.vxplore.newjayadistributor.model.Datum
import com.vxplore.newjayadistributor.model.LocationDatum
import com.vxplore.newjayadistributor.model.ShowCartDataResponse
import com.vxplore.newjayadistributor.presentation.ViewModels.CartReviewViewModel
import com.vxplore.newjayadistributor.presentation.ViewModels.PlaceOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartReviewScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    cartList: SnapshotStateList<CartProduct> = listState(key = MyDataIds.cartList),
    taxableState: State<String> = stringState(key = MyDataIds.taxableState),
    previousState: State<String> = stringState(key = MyDataIds.previousState),
    taxState: State<String> = stringState(key = MyDataIds.taxState),
    discountState: State<String> = stringState(key = MyDataIds.discountState),
    totalState: State<String> = stringState(key = MyDataIds.totalState),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart Review",
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
                    Button(
                        onClick = {
                            notifier.notify(
                                MyDataIds.addItem
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF699E73)),
                        modifier = Modifier,
                        shape = RoundedCornerShape(20.dep)

                    ) {
                        Text(
                            text = "+ Add Items",
                            fontSize = 12.sep,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
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
                    .padding(bottom = 60.dep)
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
                Spacer(modifier = Modifier.height(16.dep))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()/*
                    .heightIn(max = 350.dep)*/,
                    contentPadding = PaddingValues(bottom = 260.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    itemsIndexed(cartList) {index,it->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 16.dep)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                ) {
                                    AsyncImage(
                                        model = it.product.image,
                                        contentDescription = "",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .height(54.dep)
                                            .width(64.dep)
                                    )
                                    Spacer(modifier = Modifier.width(8.dep))
                                    Column {
                                        Text(
                                            text = if (it.product.name.length > 10) "${
                                                it.product.name.take(
                                                    10
                                                )
                                            }..." else it.product.name,
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            fontWeight = FontWeight.ExtraBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = it.product.code,
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            //fontWeight = FontWeight.ExtraBold
                                        )
                                        Text(
                                            text = it.product.info,
                                            fontSize = 10.sep,
                                            color = Color.Black,
                                            //fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                ) {
                                    Row(
                                        /* verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center*/
                                        modifier = Modifier
                                    ) {
                                        var text by remember { mutableStateOf("CB") }

                                        Text(
                                            text = it.unit,
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                            //.padding(horizontal = 16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dep))
                                        Divider(
                                            modifier = Modifier
                                                .height(20.dep)
                                                .width(1.dep)
                                        )
                                        Spacer(modifier = Modifier.width(12.dep))
                                        Column(
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                            ) {
                                                Text(
                                                    text = "QTY: ",
                                                    fontSize = 14.sep,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier
                                                    //.padding(horizontal = 16.dp)
                                                )
                                                Text(
                                                    text = it.quantity.toString(),
                                                    fontSize = 14.sep,
                                                    color = Color.Black,
                                                    modifier = Modifier
                                                    //.padding(horizontal = 16.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dep))
                                            Text(
                                                text = it.amount_string,
                                                fontSize = 14.sep,
                                                color = Color.Black,
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                //.padding(horizontal = 16.dp)
                                            )
                                        }

                                    }
                                    IconButton(
                                        onClick = {
                                            notifier.notify(MyDataIds.delete,index)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.DeleteOutline,
                                            contentDescription = "ArrowBackIosNew",
                                            tint = Color(0xFFEC2727)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dep))
                            Divider(
                                thickness = .8.dep,
                                color = Color(0xFFEAEAEA)
                            )
                            Spacer(modifier = Modifier.height(12.dep))
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            notifier.notify(MyDataIds.confirmPlaceOrder)
                        },
                        modifier = Modifier
                            .height(50.dep)
                            //.fillMaxWidth()
                            .weight(.7f),
                        colors = ButtonDefaults.buttonColors(Color(0xFF699E73)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dep,
                            pressedElevation = 10.dep
                        ),
                        shape = RectangleShape
                    ) {
                        Text(
                            "Place Order",
                            fontSize = 16.sep,
                            color = Color.White
                        )
                    }
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFFFEB56))
                            .height(50.dep)
                            //.fillMaxWidth()
                            .weight(.3f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "₹ ${totalState.value}",
                            fontSize = 16.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

            }
            var isContentOne by remember { mutableStateOf(true) }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    //.padding(horizontal = 24.dp)
                    .padding(bottom = 50.dep)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFFF2F2F2))
                ) {
                    Text(
                        text = "Delivery Point",
                        fontSize = 16.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dep))
                    Box (
                        modifier = Modifier
                        .padding(horizontal = 16.dp)
                            .border(1.dep, Color(0xFFDDDDDD),RoundedCornerShape(4.dep))
                            .fillMaxWidth()
                            .padding(vertical = 16.dep)
                    ){
                        LocationDropDown()
                    }

                    Spacer(modifier = Modifier.height(12.dep))
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFF2F2F2))
                            .clickable {
                                isContentOne = !isContentOne
                            }
                            .padding(top = 8.dep, bottom = 16.dep),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        if (isContentOne) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 20.dep)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Previous Outstanding",
                                    fontSize = 16.sep,
                                    color = Color.Black,
                                    //fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "₹ ${previousState.value}",
                                    fontSize = 16.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                        else {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 20.dep)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Previous Outstanding",
                                    fontSize = 16.sep,
                                    color = Color.Black,
                                    //fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "₹ ${previousState.value}",
                                    fontSize = 16.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 20.dep)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Taxable Amount ",
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
                        }
                        if (!isContentOne) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
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
                            Spacer(modifier = Modifier.height(12.dp))
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
    }
}

@Composable
fun LocationDropDown(
    location: SnapshotStateList<LocationDatum> = listState(key = MyDataIds.location),
    notifier: NotificationService = rememberNotifier()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select Deliver Point") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { expanded = true }
            .fillMaxWidth()
    ) {
        Text(
            text = selectedItem,
            fontSize = 14.sep,
            color = Color(0xFF222222),
            modifier = Modifier
                .padding(start = 10.dep)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier
                .padding(end = 16.dep)
                .height(24.dep)
                .width(24.dep)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                tint = Color(0xFF666666)
            )
        }
    }
    if (expanded) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                location.forEach { item ->
                    DropdownMenuItem(
                        {
                            Text(
                                text = "${item.name} - ${item.state} - ${item.pincode}",
                                fontSize = 14.sep,
                                color = Color(0xFF222222)
                            )
                        },
                        onClick = {
                            notifier.notify(MyDataIds.selectLocationId, item.id)
                            selectedItem ="${item.name} - ${item.state} - ${item.pincode}"
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
