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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyStockScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
    selectedCategoryId: State<String> = stringState(key = MyDataIds.selectedCategoryId),
    productQty: State<String> = stringState(key = MyDataIds.productQty)
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

            Spacer(modifier = Modifier.height(16.dep))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dep),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dep)
            ) {
                items(40) { index ->
                    ItemCategoryTabItemUi(
                        isSelected = index == selectedItem,
                        onClick = { selectedItem = index }
                    )
                }
            }
            // }
            Spacer(modifier = Modifier.height(16.dep))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 60.dep),
                verticalArrangement = Arrangement.spacedBy(20.dep)
            ) {
                items(count = 12) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 16.dep)
                            .fillMaxWidth()
                    ){
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                        ){
                            AsyncImage(
                                model = R.drawable.jayasales,
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(64.dep)
                                    .width(72.dep)
                            )
                            Spacer(modifier = Modifier.width(8.dep))
                            Column {
                                Text(
                                    text = "Butter D-Lite",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "10016",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    //fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "300 x 16 Pcs. Per CB",
                                    fontSize = 10.sep,
                                    color = Color.Black,
                                    //fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                        ){
                            Text(
                                text = "Pcs.",
                                fontSize = 14.sep,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                            )
                            Spacer(modifier = Modifier.width(4.dep))
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Box (
                                    modifier = Modifier
                                        .height(60.dep)
                                        .width(60.dep)
                                ){
                                    OutlinedTextField(
                                        value = productQty.value,
                                        onValueChange = {
                                            notifier.notify(MyDataIds.productQty, it)
                                        },
                                        placeholder = {
                                            Text(
                                                text = "quantity",
                                                color = Color(0XFF898989),
                                                fontSize = 6.sep
                                            )
                                        },
                                        maxLines = 1,

                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }
                                Text(
                                    text = "CB = 4.8",
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

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                /*.padding(horizontal = 24.dep)
                .padding(bottom = 60.dep)*/
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    notifier.notify(MyDataIds.stockUpdate,)
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
                if (loadingState.value) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                } else {
                    Text(
                        "Update",
                        fontSize = 18.sep,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCategoryTabItemUi(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = if (isSelected)
            ButtonDefaults.buttonColors(Color(0XFF1FB574))
        else
            ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = "Jaya",
            fontSize = 14.sep,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}
