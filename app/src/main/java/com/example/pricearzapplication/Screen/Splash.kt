package com.example.pricearzapplication.Screen

import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pricearzapplication.Gold.ApiClient
import com.example.pricearzapplication.R
import com.example.pricearzapplication.Time.TimeClient
import com.example.pricearzapplication.navigation.Screen
import com.example.pricearzapplication.ui.theme.font_bold
import com.example.pricearzapplication.ui.theme.font_medium
import com.example.pricearzapplication.ui.theme.font_standard
import com.example.pricearzapplication.ui.theme.h1
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
                    delay(1200)
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
    }
    LaunchedEffect(refreshe){
        scope.launch(Dispatchers.IO) {
            val responce = try {
                TimeClient.aoi.getTime(true)
            } catch (e: Exception) {
                return@launch
            }
            if (responce.isSuccessful) {
                withContext(Dispatchers.Main) {
                    val date = responce.body()!!.date
                    timeRozf.value =date.l
                    timeRoza.value=date.j
                    timeMah.value =date.F
                    timeSal.value=date.Y
                }
            }
        }
    }
    NoInternetDialog()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter){
            SplashScreen()
        }
        AnimatedVisibility(showButton) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(11.dp), contentAlignment = Alignment.BottomCenter)
            {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    refreshe = true
                },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                    AnimatedVisibility(refreshe) {
                        Loading3Dot()
                    }
                    AnimatedVisibility(!refreshe) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Rounded.Refresh,
                                contentDescription = "",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "تلاش مجدد",
                                fontFamily = font_standard,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }

                    }

                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 130.dp),
            contentAlignment = Alignment.BottomCenter){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "قیمت طلا و ارز",
                    color = Color(0xFF000000),
                    fontWeight = FontWeight.Bold,
                    fontFamily = font_bold,
                    fontSize = 30.sp
                )
                   Text(text = "جدید ترین قیمت ارز و طلای روز جهان",
                    color = Color.DarkGray,
                    fontFamily = font_medium,
                    fontSize = 20.sp,
                )


            }

        }

    }

}


@Composable
fun SplashScreen() {
    val composetion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash))
    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 130.dp),
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
                .fillMaxHeight(0.47f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "عدم اتصال اینترنت",
                    style = h1,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.network),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "برای ادامه لازم است به اینترنت متصل شوید یا دوباره تلاش کنید",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = font_standard
                )
                Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun Loading3Dot(){
    val composetion by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading3dotsdark))
    LottieAnimation(composition = composetion,
        iterations = LottieConstants.IterateForever
    )
}
