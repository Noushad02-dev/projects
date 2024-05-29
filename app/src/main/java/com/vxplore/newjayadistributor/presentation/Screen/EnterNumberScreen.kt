package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberBoolState
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.debduttapanda.j3lib.stringState
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R

@Composable
fun EnterNumberScreen(
    notifier: NotificationService = rememberNotifier(),
    number: State<String> = stringState(key = MyDataIds.number),
    loading: State<Boolean> = rememberBoolState(id = MyDataIds.loading)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 120.dep)
            .padding(horizontal = 32.dep)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.cropped_logo_3x),
            contentDescription = "",
            modifier = Modifier
                .height(120.dep)
                .width(196.dep),
        )
        Spacer(modifier = Modifier.height(12.dep))
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 23.sep,
            letterSpacing = 0.03.sep,
            color = Color(0xFF191919),
        )
        Spacer(modifier = Modifier.height(12.dep))
        Text(
            text = stringResource(R.string.login),
            fontSize = 20.sep,
            letterSpacing = 0.03.sep,
            color = Color(0xFF191919),
        )
        Spacer(modifier = Modifier.height(40.dep))
        OutlinedTextField(
            value = number.value,
            onValueChange = {
                notifier.notify(MyDataIds.number, it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    "Enter your Mobile Number",
                    color = Color(0xFFC7C7C7)
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sep
            ),
            singleLine = true,
            modifier = Modifier
                .background(color = Color.White)
                .height(61.dep)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFF929191),
            ),
        )
        Spacer(modifier = Modifier.height(12.dep))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF606060)),
            shape = RoundedCornerShape(4.dep),
            modifier = Modifier
                .height(60.dep)
                .fillMaxWidth(),
            onClick = {
                notifier.notify(MyDataIds.btnVerify)
            },
        ) {
            if (loading.value) {
                CircularProgressIndicator(
                    color = Color.White
                )
            } else {
                Text(
                    "Get OTP Verification",
                    color = Color(0xFFC7C7C7),
                    fontSize = 18.sep,
                    modifier = Modifier
                )
            }
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding(bottom = 20.dep)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.licence),
            fontSize = 14.sep,
            letterSpacing = 0.06.sep,
            color = Color(0xFFC7C7C7),
            modifier = Modifier
        )
    }
}