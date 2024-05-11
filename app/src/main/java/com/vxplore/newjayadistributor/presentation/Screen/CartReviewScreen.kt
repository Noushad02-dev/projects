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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R
import com.vxplore.newjayadistributor.presentation.ViewModels.PlaceOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartReviewScreen(
    notifier: NotificationService = rememberNotifier(),
    loadingState: State<Boolean> = boolState(key = MyDataIds.loadingState),
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dep))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dep),
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
                            val viewModel: PlaceOrderViewModel = viewModel()
                            var text by remember { mutableStateOf("CB") }

                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color(0xFFD1D1D1), RoundedCornerShape(4.dp))
                                    .height(44.dep)
                                    .width(60.dep),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(
                                    text = text,
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                    //.padding(horizontal = 16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dep))
                            Box (
                                modifier = Modifier
                                    .border(1.dp, Color(0xFFD1D1D1), RoundedCornerShape(4.dp))
                                    .height(44.dep)
                                    .width(60.dep),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "920",
                                    fontSize = 14.sep,
                                    color = Color.Black,
                                    modifier = Modifier
                                    //.padding(horizontal = 16.dp)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        notifier.notify(MyDataIds.confirmPlaceOrder,)
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
                    if (loadingState.value) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    } else {
                        Text(
                            "Place Order",
                            fontSize = 16.sep,
                            color = Color.White
                        )
                    }
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
                    if (loadingState.value) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    } else {
                        Text(
                            "₹ 160000",
                            fontSize = 16.sep,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
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
                    .clickable {
                        isContentOne = !isContentOne
                    }
                    .padding(top = 8.dep, bottom = 16.dep),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isContentOne){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Previous Outstanding" ,
                        fontSize = 16.sep,
                        color = Color.Black,
                        //fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "₹ 200000",
                        fontSize = 16.sep,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                }else{
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 20.dep)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Previous Outstanding" ,
                            fontSize = 16.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ 200000",
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
                            text = "Taxable Amount " ,
                            fontSize = 14.sep,
                            color = Color.Black,
                            //fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹ 100000",
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
                            text = "₹ 245",
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
                            text = "₹ 0",
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
                            text = "₹ 1845",
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