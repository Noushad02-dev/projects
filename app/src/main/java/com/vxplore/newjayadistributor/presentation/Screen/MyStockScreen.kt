package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.AsyncImage
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.intState
import com.debduttapanda.j3lib.listState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.App
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R
import com.vxplore.newjayadistributor.model.CategoriesDataResponse
import com.vxplore.newjayadistributor.model.Datum
import com.vxplore.newjayadistributor.model.MyStockDatum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStockScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    selectedCategoryId: State<String> = stringState(key = MyDataIds.selectedCategoryId),
    productQty: State<String> = stringState(key = MyDataIds.productQty),
    categoryList: SnapshotStateList<CategoriesDataResponse.CategoriesData> = listState(key = MyDataIds.categoryList),
    selectedTabIndex: State<Int> = intState(key = MyDataIds.brandChange),
    productList: SnapshotStateList<MyStockDatum> = listState(key = MyDataIds.productList),
    lostInternet: State<Boolean> = boolState(key = MyDataIds.lostInternet),
) {
    var selectedItem by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "My Stock",
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            /*Column (
                modifier = Modifier
                    .background(Color(0xFFF5F5F5))
                    .fillMaxWidth()
                    .padding(12.dep)
            ){*/

            if (lostInternet.value) {
                LostInternet_ui(onDismissRequest = { notifier.notify(MyDataIds.onDissmiss) })
            }
            Spacer(modifier = Modifier.height(16.dep))
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                contentColor = Color.Black,
                containerColor = Color.Transparent,
                edgePadding = 0.dep,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    if (selectedTabIndex.value > -1 && tabPositions.isNotEmpty()) {
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(
                                currentTabPosition = tabPositions[selectedTabIndex.value]
                            ),
                            color = Color.Red
                        )
                    }
                }
            )
            {
                categoryList.forEachIndexed { tabIndex, tab ->
                    Tab(
                        selected = selectedTabIndex.value == tabIndex,
                        onClick = {
                            notifier.notify(MyDataIds.brandChange, tabIndex)
                        },
                        text = {
                            Text(
                                text = tab.name,
                                fontSize = 12.sep,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.Black
                    )
                }
            }
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
                Spacer(modifier = Modifier.height(16.dep))
                if (productList.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "No product(s) available",
                            fontSize = 16.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 60.dep),
                    verticalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    itemsIndexed(productList) { index, it ->
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
                                        .height(64.dep)
                                        .width(72.dep)
                                )
                                Spacer(modifier = Modifier.width(8.dep))
                                Column {
                                    Text(
                                        text = it.product_name,
                                        fontSize = 14.sep,
                                        color = Color.Black,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        text = it.code,
                                        fontSize = 14.sep,
                                        color = Color.Black,
                                        //fontWeight = FontWeight.ExtraBold
                                    )
                                    Text(
                                        text = it.info,
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
                                Text(
                                    text = "Pcs.",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold,
                                )
                                Spacer(modifier = Modifier.width(4.dep))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .border(1.dep, Color.LightGray)
                                            .height(58.dep)
                                            .width(64.dep)
                                    ) {
                                        MyStockProductList(
                                            it,
                                            onQuantityChange = {},
                                            index = index,
                                            it
                                        )
                                    }
                                    Text(
                                        text = "CB = ${it.cb_quantity_string}",
                                        fontSize = 10.sep,
                                        color = Color.Black,
                                        //fontWeight = FontWeight.ExtraBold,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dep))
                        Divider(
                            thickness = .8.dep,
                            color = Color(0xFFEAEAEA)
                        )
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
            Button(
                onClick = {
                    notifier.notify(MyDataIds.stockUpdate)
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
                Text(
                    "Update",
                    fontSize = 18.sep,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MyStockProductList(
    it: MyStockDatum,
    onQuantityChange: (Int) -> Unit,
    index: Int,
    product: MyStockDatum,
    notifier: NotificationService = rememberNotifier(),
) {
    var quantity by remember { mutableStateOf(App.cart.getQuantity(product.product_id)) }
    val qtyState = remember { mutableStateOf<String?>(null) }

    // Ensure qtyState is updated when quantity changes
    LaunchedEffect(quantity) {
        qtyState.value = if (quantity > 0) quantity.toString() else null
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = qtyState.value ?: "",
            onValueChange = {
                qtyState.value = it
                if (it.isNotEmpty()) {
                    product.unit.forEach { unit ->
                        App.cart.stockadd(product.product_id, it.toInt())
                    }
                } else {
                    // App.cart.remove(product.uid) // Remove if input is empty
                }
            },
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text(
                    text = product.pcs_quantity,
                    fontSize = 12.sep,
                    color = Color(0xFF727272)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 16.sep, color = Color.Black),
            singleLine = true
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}


