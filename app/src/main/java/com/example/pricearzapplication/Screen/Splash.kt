package com.example.pricearzapplication.Screen

import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.window.SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pricearzapplication.Gold.ApiClient
import com.example.pricearzapplication.R
import com.example.pricearzapplication.Time.TimeClient
import com.example.pricearzapplication.navigation.Screen
import com.example.pricearzapplication.ui.theme.font_standard
import com.example.pricearzapplication.ui.theme.h1
import com.example.pricearzapplication.ui.theme.h2
import com.example.pricearzapplication.ui.theme.h3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(navHostController: NavHostController) {
    var refreshe by remember {
        mutableStateOf(false)
    }

    var showButton by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(refreshe) {
        scope.launch(Dispatchers.IO) {
            val responce = try {
                ApiClient.api.getAllItem()
            } catch (e: Exception) {
                Log.e("pasi", "erorr")
                withContext(Dispatchers.Main) {
                    showDialogNoInternet.value = true
                    showButton = true
                    refreshe = false
                }
                return@launch
            }
            if (responce.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Crypto.value = responce.body()!!.data.cryptocurrencies
                    Arz.value = responce.body()!!.data.currencies
                    Gold.value = responce.body()!!.data.golds
                    time.value = responce.body()!!.message
                    delay(1000)
                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    showDialogNoInternet.value = true
                    showButton = true
                    refreshe = false
                }
            }
        }
        launch(Dispatchers.IO) {
            val responce = try {
                TimeClient.aoi.getTime(true)
            } catch (e: Exception) {
                return@launch
            }
            if (responce.isSuccessful) {
                withContext(Dispatchers.Main) {
                    val date = responce.body()!!.date
                    DateTime.value = "${date.l} ${date.j} ${date.F} ${date.Y}"
                }
            }
        }
    }
    NoInternetDialog()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SplashScreen()
        AnimatedVisibility(refreshe) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        AnimatedVisibility(showButton) {
            TextButton(onClick = {
                refreshe = true
            }) {
                Icon(
                    Icons.Rounded.Refresh,
                    contentDescription = "",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text(
                    text = "لطفا وضعیت اتصال اینترنت خود را برسی کنید",
                    fontFamily = font_standard,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }


            //                Icon(
            //                    painter = painterResource(id = R.drawable.logo),
            //                    contentDescription = "",Modifier.size(300.dp,120.dp) ,tint = Color.Black,
            //
            //                )
        }

    }

}


@Composable
fun SplashScreen() {
    val composetion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash))
    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .height(410.dp),
        composition = composetion,
        iterations = LottieConstants.IterateForever,
    )
}

var showDialogNoInternet = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoInternetDialog() {

    if (showDialogNoInternet.value) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { showDialogNoInternet.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .height(405.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.network),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "برای ادامه لازم است به اینترنت متصل شوید یا دوباره تلاش کنید",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = font_standard
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val context = LocalContext.current
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0B5895)
                        ),
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(11.dp),
                        onClick = {
                            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                            context.startActivity(intent)
                        }) {
                        Text(
                            text = "اینترنت وای فای",
                            color = Color.White,
                            style = h3
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(11.dp),
                        onClick = {
                            val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
                            context.startActivity(intent)
                        }) {
                        Text(
                            text = "اینترنت موبایل",
                            color = Color.Black,
                            style = h3
                        )
                    }
                }
            }
        }
    }
}
