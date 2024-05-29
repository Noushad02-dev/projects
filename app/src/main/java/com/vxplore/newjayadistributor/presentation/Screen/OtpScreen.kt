package com.vxplore.newjayadistributor.presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
fun OtpScreen(
    notifier: NotificationService = rememberNotifier(),
    otp: State<String> = stringState(key = MyDataIds.otp),
    loading: State<Boolean> = rememberBoolState(id = MyDataIds.loading),
    numberState: State<String> = stringState(key = MyDataIds.numberState),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequesters = List(4) { FocusRequester() }
    DisposableEffect(otp) {
        if (otp.value.length < 4) {
            keyboardController?.show()
            focusRequesters[otp.value.length].requestFocus()
        } else {
            keyboardController?.hide()
        }
        onDispose { }
    }
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
                .height(120.dp)
                .width(196.dp),
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
            text = stringResource(R.string.verify_otp),
            fontSize = 18.sep,
            letterSpacing = 0.03.sep,
            color = Color(0xFF191919),
        )
        Spacer(modifier = Modifier.height(40.dep))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .border(.5.dep, Color(0xFF707070), RoundedCornerShape(4.dep))
                .height(60.dep)
                .fillMaxWidth()
                .padding(horizontal = 20.dep)
        ) {
            Text(
                text = numberState.value,
                fontSize = 20.sep,
                letterSpacing = 0.06.sep,
                color = Color(0xFFC7C7C7),
            )
            IconButton(onClick = {
            }) {
                Icon(
                    painterResource(id = R.drawable.edit),
                    contentDescription = "",
                    modifier = Modifier
                        .height(20.dep)
                        .width(20.dep),
                    tint = Color(0xFFC7C7C7)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dep))
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dep),
            value = otp.value,
            onValueChange = {
                if (it.length <= 4) {
                    notifier.notify(MyDataIds.otp, it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(4) { index ->
                        val input = when {
                            index >= otp.value.length -> ""
                            else -> otp.value[index].toString()
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dep))
                                .border(
                                    .5.dep,
                                    Color.Red.takeIf { index == otp.value.length }
                                        ?: Color(0xFF707070),
                                    RoundedCornerShape(4.dep)
                                )
                                .focusRequester(focusRequesters[index]),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = input,
                                fontSize = 24.sep,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF1E2330),
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dep))
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dep))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF606060)),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            onClick = {
                notifier.notify(MyDataIds.verify)
            },
        ) {
            if (loading.value) {
                CircularProgressIndicator(
                    color = Color.White
                )
            } else {
                Text(
                    stringResource(id = R.string.verify),
                    color = Color(0xFFC7C7C7),
                    fontSize = 18.sep,
                    modifier = Modifier
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dep))
        TextButton(
            onClick = {
                notifier.notify(MyDataIds.resend)
            }) {
            Text(
                stringResource(id = R.string.resend),
                color = Color(0xFF191919),
                fontSize = 18.sp,
                modifier = Modifier
            )
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
        )
    }
}