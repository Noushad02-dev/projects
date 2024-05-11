package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R

@Composable
fun ThankYouScreen(
    notifier: NotificationService = rememberNotifier(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color(0xFF329B46), CircleShape)
                .size(80.dep)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "ArrowBackIosNew",
                tint = Color.White,
                modifier = Modifier
                    .height(60.dep)
                    .width(60.dep)
            )
        }
        Spacer(modifier = Modifier.height(16.dep))
        Text(
            "Your Order is",
            fontSize = 28.sep,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(2.dep))
        Text(
            "Confirmed!",
            fontSize = 28.sep,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(12.dep))
        Text(
            "Thanks for your order!",
            fontSize = 14.sep,
            color = Color(0xFF666666),
           // fontWeight = FontWeight.ExtraBold
        )
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
                notifier.notify(MyDataIds.backToDashboard,)
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
                "Back to Dashboard",
                fontSize = 18.sep,
                color = Color.White
            )
            //}
        }
    }
}