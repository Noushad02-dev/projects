package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.vxplore.newjayadistributor.model.AddProductListData
import com.vxplore.newjayadistributor.model.CartProduct
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.OrderDetailsDatum
import com.vxplore.newjayadistributor.presentation.ViewModels.DueDeliveryDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DueDeliveryDetailsScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    profilename: State<String> = stringState(key = MyDataIds.profilename),
    dialog: Boolean = boolState(key = MyDataIds.openDialog).value,
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
    if (dialog == true) {
        Dialogue_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
    }
    var showBottomSheetState by remember { mutableStateOf(false) }
    var isBottomSheetLaidOut by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Due Delivery Details",
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
                    verticalAlignment = Alignment.CenterVertically,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Price",
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        IconButton(
                            onClick = {
                                /*notifier.notify(
                                    MyDataIds.back
                                )*/
                            },
                            enabled = false
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White,
                            )
                        }
                        IconButton(
                            onClick = {
                                /*notifier.notify(
                                    MyDataIds.back
                                )*/
                            },
                            enabled = false
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dep))
                Divider(
                    thickness = .8.dep,
                    color = Color(0xFFEBEBEB)
                )
                Spacer(modifier = Modifier.height(6.dep))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 208.dep),
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
                                            fontSize = 12.sep,
                                            color = Color.Black,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Spacer(modifier = Modifier.height(4.dep))
                                        Text(
                                            text = "${orderedProduct.product.code} - ${orderedProduct.product.weight_string}",
                                            fontSize = 12.sep,
                                            color = Color.Black,
                                            //fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                    Text(
                                        text = "₹ ${orderedProduct.price_string} X ${orderedProduct.quantity}",
                                        fontSize = 12.sep,
                                        color = Color.Black,
                                        //fontWeight = FontWeight.ExtraBold
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "₹ ${orderedProduct.sub_total_amount_string}",
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        IconButton(
                                            onClick = {
                                                showBottomSheetState = true
                                                notifier.notify(
                                                    MyDataIds.update,
                                                    Triple(
                                                        index,
                                                        orderedProduct.product_id,
                                                        orderedProduct.product.name
                                                    )
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Edit",
                                                tint = Color(0xFFD0D0D0),
                                                modifier = Modifier
                                                    .height(32.dep)
                                                    .width(20.dep)
                                            )
                                        }
                                        IconButton(
                                            onClick = {
                                                notifier.notify(
                                                    MyDataIds.remove,
                                                    Pair(index, orderedProduct.product_id)
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "DeleteOutline",
                                                tint = Color(0xFFD62B2B)
                                            )
                                        }
                                    }
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
                            notifier.notify(MyDataIds.confirmDispatch)
                        },
                        modifier = Modifier
                            .height(50.dep)
                            //.fillMaxWidth()
                            .weight(.6f),
                        colors = ButtonDefaults.buttonColors(Color(0xFF699E73)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dep,
                            pressedElevation = 10.dep
                        ),
                        shape = RectangleShape
                    ) {
                        if (loadingState.value) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        } else {
                            Text(
                                "Confirm Dispatch",
                                fontSize = 16.sep,
                                color = Color.White
                            )
                        }
                    }
                    Button(
                        onClick = {
                            //notifier.notify(MyDataIds.orderConfirm,)
                        },
                        modifier = Modifier
                            .height(50.dep)
                            //.fillMaxWidth()
                            .weight(.4f),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFEB56)),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dep,
                            pressedElevation = 10.dep
                        ),
                        shape = RectangleShape
                    ) {
                        if (loadingState.value) {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        } else {
                            Text(
                                "Download",
                                fontSize = 16.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    //.padding(horizontal = 24.dep)
                    .padding(bottom = 50.dep)
                    .fillMaxSize()
            ) {
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
    if (showBottomSheetState) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheetState = false
            },
            sheetState = sheetState
        ) {
            NameBottomSheetContent(
                onClearIconClick = {
                    showBottomSheetState = false
                }, // Provide a default empty click handler
                onLaidOut = {
                    isBottomSheetLaidOut = true
                },
                onUpdateQuantity = { /* Implement the update logic here */ },
                profilename = profilename // Pass the state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBottomSheetContent(
    onClearIconClick: () -> Unit,
    onLaidOut: () -> Unit,
    onUpdateQuantity: (String) -> Unit,
    notifier: NotificationService = rememberNotifier(),
    profilename: State<String> = stringState(key = MyDataIds.profilename),
    bottomSheetVisible: MutableState<Boolean> = remember { mutableStateOf(true) },
    qtyState: State<String> = stringState(key = MyDataIds.qtyState),
    //qty: State<String> = stringState(key = MyDataIds.qtyState),
    productNameState: State<String> = stringState(key = MyDataIds.productNameState),
) {
    var visible by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onLaidOut()
        onDispose { }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 12.dep, end = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = productNameState.value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C323A),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .clickable {
                        visible = !visible
                        onClearIconClick.invoke()
                    }
                    .padding(start = 12.dp)
                    .height(32.dp)
                    .width(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    tint = Color(0xFFEB3D34)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Change the quantity",
            fontSize = 12.sp,
            color = Color(0xFF2C323A),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = profilename.value,
            onValueChange = {
                notifier.notify(MyDataIds.profilename, it)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .imePadding()
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color(0xFF2C323A),
                fontSize = 12.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFEB3D34),
                unfocusedBorderColor = Color(0xFF959595)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(
                    "Enter quantity",
                    color = Color(0xFF959595),
                    fontSize = 12.sp
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                bottomSheetVisible.value = false
                onClearIconClick.invoke()
                notifier.notify(MyDataIds.updateQuantity)
            },
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .height(54.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFFEB3D34)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun Dialogue_ui(
    onDismissRequest: () -> Unit,
    notifier: NotificationService = rememberNotifier(),
    productQty: State<String> = stringState(key = MyDataIds.productQty),
    productPrice: State<String> = stringState(key = MyDataIds.productPrice),
    addProductList: SnapshotStateList<AddProductListData> = listState(key = MyDataIds.addProductList),
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    //.padding(horizontal = 16.dep)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(Color(0xFFFFEB56))
                        //.padding(horizontal = 16.dep)
                        .fillMaxWidth()
                        .padding(vertical = 8.dep)
                        .padding(horizontal = 16.dep)
                ) {
                    Text(
                        text = "Add Product",
                        fontSize = 20.sep,
                        color = Color.Black,
                        modifier = Modifier
                        //.padding(horizontal = 16.dep)
                    )
                    IconButton(
                        onClick = {
                            notifier.notify(
                                MyDataIds.cancelDialog
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "Cancel",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dep))
                PartiesSearchBox()
                Spacer(modifier = Modifier.height(28.dep))
                if (addProductList.isEmpty()) {
                    Text(
                        text = "Search the product",
                        fontSize = 16.sep,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(bottom = 20.dep)
                    )
                } else {

                    var amountString by remember { mutableStateOf("") }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(bottom = 10.dep),
                        verticalArrangement = Arrangement.spacedBy(20.dep)
                    ) {
                        item {
                            val orderDetail = addProductList.firstOrNull()
                            orderDetail?.let {
                                val unit = orderDetail.unit.first()
                                var text by remember { mutableStateOf("${unit.name}") }
                                // Initialize amountString based on the initial unit
                                if (text == "CB") {
                                    amountString = unit.amount_string_cb ?: ""
                                } else {
                                    amountString = unit.amount_string_pcs ?: ""
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
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
                                                model = it.image,
                                                contentDescription = "",
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .height(60.dep)
                                                    .width(72.dep)
                                            )
                                            Spacer(modifier = Modifier.width(8.dep))
                                            Column {
                                                Text(
                                                    text = it.name,
                                                    fontSize = 14.sep,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                                Text(
                                                    text = "",
                                                    fontSize = 2.sep,
                                                    color = Color.Black,
                                                    //fontWeight = FontWeight.ExtraBold
                                                )
                                                Text(
                                                    text = it.info,
                                                    fontSize = 10.sep,
                                                    color = Color(0xFF666666),
                                                    //fontWeight = FontWeight.ExtraBold
                                                )
                                            }
                                        }
                                        Text(
                                            text = it.code,
                                            fontSize = 14.sep,
                                            color = Color.Black,
                                            modifier = Modifier
                                            //.padding(horizontal = 16.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(12.dep))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dep)
                                            .fillMaxWidth()
                                    ) {
                                        val viewModel: DueDeliveryDetailsViewModel = viewModel()
                                        var text by remember { mutableStateOf("${unit.name}") }

                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color(0xFFD1D1D1),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .height(64.dp)
                                                .width(80.dp)
                                                .clickable {
                                                    val newText = if (text == "CB") {
                                                        viewModel.setSelectedText(
                                                            "Pcs",
                                                            "unit_pcs"
                                                        )
                                                        "Pcs"
                                                    } else {
                                                        viewModel.setSelectedText(
                                                            "CB",
                                                            "unit_cb"
                                                        )
                                                        "CB"
                                                    }
                                                    text = newText
                                                    if (text == "CB") {
                                                        amountString = unit.amount_string_cb ?: ""
                                                    } else {
                                                        amountString = unit.amount_string_pcs ?: ""
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = text,
                                                fontSize = 12.sp,
                                                color = Color.Black,
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color(0xFFD1D1D1),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .height(64.dep)
                                                .width(80.dep),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            OutlinedTextField(
                                                value = productQty.value,
                                                onValueChange = {
                                                    notifier.notify(MyDataIds.productQty, it)
                                                },
                                                placeholder = {
                                                    Text(
                                                        text = "quantity",
                                                        color = Color(0XFF898989),
                                                        fontSize = 10.sep
                                                    )
                                                },
                                                maxLines = 1,
                                                textStyle = TextStyle(
                                                    fontSize = 14.sep
                                                ),

                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Done
                                                ),
                                                singleLine = true,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                            )
                                        }
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color(0xFFD1D1D1),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .height(64.dep)
                                                .width(80.dep),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            amountString = if (text == "CB") unit.amount_string_cb ?: "" else unit.amount_string_pcs ?: ""
                                            OutlinedTextField(
                                                value = productPrice.value,
                                                onValueChange = {
                                                    amountString = it
                                                    notifier.notify(MyDataIds.productPrice, it)
                                                },
                                                placeholder = {
                                                    Text(
                                                        text = if (text == "CB") {
                                                            unit.amount_string_cb ?: ""
                                                        } else {
                                                            unit.amount_string_pcs ?: ""
                                                        },
                                                        color = Color(0XFF898989),
                                                        fontSize = 14.sp
                                                    )
                                                },
                                                maxLines = 1,
                                                textStyle = TextStyle(
                                                    fontSize = 14.sep
                                                ),

                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Number,
                                                    imeAction = ImeAction.Done
                                                ),
                                                singleLine = true,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dep))
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.addProduct)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF699E73)),
                    shape = RoundedCornerShape(4.dep),
                    modifier = Modifier
                        .padding(bottom = 12.dep)
                        .padding(horizontal = 16.dep)
                        .fillMaxWidth()
                        .height(52.dep)
                ) {
                    Text(
                        text = "Add Product",
                        fontSize = 16.sep,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun PartiesSearchBox(
    notifier: NotificationService = rememberNotifier(),
    partiesSearch: State<String> = stringState(key = MyDataIds.partiesSearch)
) {
    OutlinedTextField(
        value = partiesSearch.value,
        onValueChange = {
            notifier.notify(MyDataIds.partiesSearch, it)
        },
        placeholder = {
            Text(
                text = "Search Product",
                color = Color(0XFF898989),
                fontSize = 16.sep
            )
        },
        maxLines = 1,
        trailingIcon = {
            IconButton(
                onClick = {
                    notifier.notify(MyDataIds.search)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color(0XFF898989)
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        modifier = Modifier
            .padding(horizontal = 16.dep)
            .fillMaxWidth()
    )
}

