package com.vxplore.newjayadistributor.presentation.Screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberStringState
import com.debduttapanda.j3lib.sep
import com.vxplore.newjayadistributor.MyDataIds
import com.vxplore.newjayadistributor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    versionName: State<String> = rememberStringState(id = MyDataIds.versionName),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                },
                navigationIcon = {
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB56)
                )
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .background(color = Color(0xFFFFEB56))
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.jayasales),
                contentDescription = "",
                modifier = Modifier
                    .height(120.dep)
                    .width(196.dep),
            )
            Spacer(modifier = Modifier.height(12.dep))
            Text(
                "Jaya Distributor",
                fontSize = 20.sep,
                letterSpacing = (-0.03).sep,
                color = Color(0xFF222222)
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(bottom = 30.dep)
                .fillMaxSize()
        ) {
            Text(
                text = "Version : ${versionName.value}",
                fontSize = 14.sep,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
