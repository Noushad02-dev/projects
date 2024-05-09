package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notifier: NotificationService = rememberNotifier(),
    openLogoutDialog: Boolean = boolState(key = MyDataIds.opendialog).value,
    nameState : State<String> = stringState(key = MyDataIds.nameState),
    emailState: State<String> = stringState(key = MyDataIds.emailState),
){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(0.dep),
                modifier = Modifier
                    .padding(end = 68.dep)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(28.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dep)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "",
                            modifier = Modifier
                                .size(
                                    width = 64.dep,
                                    height = 64.dep
                                )
                                .shadow(2.dep, shape = CircleShape)
                        )
                        Card(
                            colors = CardDefaults.cardColors(Color.White),
                            shape = CircleShape,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(20.dep),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep
                            )
                        ) {
                            IconButton(
                                onClick = {
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(5.dep)
                                        .size(width = 12.dep, height = 12.dep),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        Text(
                            text = "nameState.value",
                            fontSize = 18.sep,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.height(2.dep))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "",
                                tint = Color(0xFFD62B2B),
                                modifier = Modifier.size(width = 16.dep, height = 20.dep)
                            )
                            Text(
                                text = "emailState.value",
                                fontSize = 12.sep,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFFFEB56))
                        .fillMaxWidth()
                        .padding(20.dep)
                ) {
                    Text(
                        text = "My Store Distributor",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Spacer(modifier = Modifier.height(20.dep))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "Dashboard",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "Orders",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "My Stocks",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "My Offers",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "My Orders",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "Place Order",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "Track Orders",
                        fontSize = 16.sep,
                        color = Color(0xFF1D1A1A)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            notifier.notify(MyDataIds.logout)
                        }
                        .padding(12.dep)
                ) {
                    Text(
                        text = "Logout",
                        fontSize = 16.sep,
                        color = Color(0xFF222222)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        val openDialog = remember { mutableStateOf(false) }
        if (openLogoutDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Login",
                    )
                },
                text = {
                    Text("Are you sure to logout")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            notifier.notify(MyDataIds.Confirm)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFB81B1B))
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            notifier.notify(MyDataIds.dismiss)
                        }) {
                        Text("Dismiss")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "",
                        tint = Color(0xFFB81B1B),
                        modifier = Modifier
                            .size(24.dep)
                    )
                }
            )
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .shadow(2.dep),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.jayasales),
                            contentDescription ="",
                            modifier = Modifier
                                .height(60.dp)
                                .width(100.dp),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier
                                .height(20.dep)
                                .width(32.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "",
                                tint = Color(0xFF191919)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color(0xFFFFEB56)),
                    actions = {
                        IconButton(
                            onClick = {
                                //notifier.notify(MyDataIds.notification)
                            },
                            modifier = Modifier
                                .height(36.dep)
                                .width(40.dep)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = "",
                                tint = Color(0xFF191919)
                            )
                        }
                    }
                )
            },
        )
        {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                Spacer(modifier = Modifier.height(20.dep))
                Row (
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ){
                    Card(
                        modifier = Modifier
                            .height(120.dep)
                            .fillMaxWidth()
                            .shadow(
                                2.dep,
                                RoundedCornerShape(4.dep),
                                clip = true,
                                DefaultShadowColor
                            )
                            .clip(RoundedCornerShape(4.dep))
                            .clickable {
                                // notifier.notify(MyDataIds.storeDetails, it)
                            }
                            .weight(.5f),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dep,
                            focusedElevation = 10.dep,
                        ),
                        shape = RoundedCornerShape(4.dep),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    Color(0xFF699E73),
                                    RoundedCornerShape(topStart = 4.dep, topEnd = 4.dep)
                                )
                                .fillMaxSize()
                                .weight(.7f)
                                .padding(horizontal = 12.dep)
                                .padding(vertical = 8.dep),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Order Received",
                                fontSize = 16.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "2",
                                fontSize = 28.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                // .fillMaxSize()
                                .weight(.3f)
                                .padding(8.dep)
                        ){
                            Text(
                                text = "₹ 1544500",
                                fontSize = 14.sep,
                                color = Color(0xFF575151),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dep))
                    Card(
                        modifier = Modifier
                            .height(120.dep)
                            .fillMaxWidth()
                            .shadow(
                                2.dep,
                                RoundedCornerShape(4.dep),
                                clip = true,
                                DefaultShadowColor
                            )
                            .clip(RoundedCornerShape(4.dep))
                            .clickable {
                                // notifier.notify(MyDataIds.storeDetails, it)
                            }
                            .weight(.5f),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dep,
                            focusedElevation = 10.dep,
                        ),
                        shape = RoundedCornerShape(4.dep),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    Color(0xFF3B3B3B),
                                    RoundedCornerShape(topStart = 4.dep, topEnd = 4.dep)
                                )
                                .fillMaxSize()
                                .weight(.7f)
                                .padding(horizontal = 12.dep)
                                .padding(vertical = 8.dep),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "My Stock",
                                fontSize = 16.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "5000",
                                fontSize = 28.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                // .fillMaxSize()
                                .weight(.3f)
                                .padding(8.dep)
                        ){
                            Text(
                                text = "₹ 1544500",
                                fontSize = 14.sep,
                                color = Color(0xFF575151),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dep))
                Row (
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ){
                    Card(
                        modifier = Modifier
                            .height(120.dep)
                            .fillMaxWidth()
                            .shadow(
                                2.dep,
                                RoundedCornerShape(4.dep),
                                clip = true,
                                DefaultShadowColor
                            )
                            .clip(RoundedCornerShape(4.dep))
                            .clickable {
                                // notifier.notify(MyDataIds.storeDetails, it)
                            }
                            .weight(.5f),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dep,
                            focusedElevation = 10.dep,
                        ),
                        shape = RoundedCornerShape(4.dep),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    Color(0xFF3B3B3B),
                                    RoundedCornerShape(topStart = 4.dep, topEnd = 4.dep)
                                )
                                .fillMaxSize()
                                .weight(.7f)
                                .padding(horizontal = 12.dep)
                                .padding(vertical = 8.dep),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Due Delivery",
                                fontSize = 16.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "4",
                                fontSize = 28.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                // .fillMaxSize()
                                .weight(.3f)
                                .padding(8.dep)
                        ){
                            Text(
                                text = "₹ 1544500",
                                fontSize = 14.sep,
                                color = Color(0xFF575151),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dep))
                    Card(
                        modifier = Modifier
                            .height(120.dep)
                            .fillMaxWidth()
                            .shadow(
                                2.dep,
                                RoundedCornerShape(4.dep),
                                clip = true,
                                DefaultShadowColor
                            )
                            .clip(RoundedCornerShape(4.dep))
                            .clickable {
                                // notifier.notify(MyDataIds.storeDetails, it)
                            }
                            .weight(.5f),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dep,
                            focusedElevation = 10.dep,
                        ),
                        shape = RoundedCornerShape(4.dep),
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    Color(0xFF3B3B3B),
                                    RoundedCornerShape(topStart = 4.dep, topEnd = 4.dep)
                                )
                                .fillMaxSize()
                                .weight(.7f)
                                .padding(horizontal = 12.dep)
                                .padding(vertical = 8.dep),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Track Order",
                                fontSize = 16.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "560",
                                fontSize = 28.sep,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                // .fillMaxSize()
                                .weight(.3f)
                                .padding(8.dep)
                        ){
                            Text(
                                text = "₹ 1544500",
                                fontSize = 14.sep,
                                color = Color(0xFF575151),
                                fontWeight = FontWeight.Bold
                            )
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
                       // notifier.notify(MyDataIds.signUpClick,)
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
                  /*  if (loading.value) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    } else {*/
                        Text(
                            "+ Place Order",
                            fontSize = 18.sep,
                            color = Color.White
                        )
                    //}
                }
            }
        }
    }
}