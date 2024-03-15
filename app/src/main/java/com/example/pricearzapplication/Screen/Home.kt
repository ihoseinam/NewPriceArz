package com.example.pricearzapplication.Screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.example.pricearzapplication.R
import com.example.pricearzapplication.DataMode.GoldData.Cryptocurrency
import com.example.pricearzapplication.DataMode.GoldData.Currency
import com.example.pricearzapplication.DataMode.GoldData.Gold
import com.example.pricearzapplication.send.SendMessage
import com.example.pricearzapplication.ui.theme.font_standard
import com.example.pricearzapplication.ui.theme.h1
import com.example.pricearzapplication.ui.theme.h2
import com.example.pricearzapplication.ui.theme.h3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var Crypto = mutableStateOf(emptyList<Cryptocurrency>())
var Gold = mutableStateOf(emptyList<Gold>())
var Arz = mutableStateOf(emptyList<Currency>())
val time = mutableStateOf("درحال بارگیری ...")
val DateTime = mutableStateOf("در حال بارگیری..")

@Composable
fun HomeScree() {
    val TxtTime by remember {
        time
    }
    val TxtDate by remember {
        DateTime
    }


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff5764DA))
                    .padding(horizontal = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = " ", tint = Color.White
                    )
                }
                Text(
                    text = "قیمت لحظه ای انوع ارز",
                    style = h1,
                    color = Color.White
                )

            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFFF7F7F7))
        ) {
            SendComment()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(Color(0xFF0F27FD)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = TxtDate,
                    style = h1,
                    color = Color.White,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFF5764DA))
                        .padding(9.dp)
                )
                Text(
                    text = TxtTime,
                    style = h3,
                    color = Color.White,
                )
            }
            TabHome()


        }

    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHome() {
    val item = listOf(
        Tab(
            "کریپتو",
        ),
        Tab(
            "طلا",

            ),
        Tab(
            "ارز",
        )
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerstate = rememberPagerState {
        item.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerstate.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerstate.currentPage) {
        selectedTabIndex = pagerstate.currentPage
    }

    val cr by remember {
        Crypto
    }

    val arz by remember {
        Arz
    }

    val tala by remember {
        Gold
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Black,
            containerColor = Color(0xffffffff),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(15.dp))
        ) {
            item.forEachIndexed { index, tab ->
                Tab(
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.DarkGray,
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tab.name,
                            style = h3
                        )
                    },
                )
            }
        }
        HorizontalPager(
            state = pagerstate,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (it) {
                0 -> Crypto(cr)
                1 -> Gold(tala)
                2 -> Currency(arz)
            }
        }
    }


}


@Composable
fun Crypto(Responce: List<Cryptocurrency>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(Responce) { index, item ->
            ItemProduct(lable = item.label, price = item.price)
        }
    }
}

@Composable
fun Gold(Responce: List<Gold>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(Responce) { index, item ->
            ItemProduct(lable = item.label, price = item.price)
        }
    }
}

@Composable
fun Currency(Responce: List<Currency>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(Responce) { index, item ->
            ItemProduct(lable = item.label, price = item.price / 10)
        }
    }
}


@Composable
fun ItemProduct(lable: String, price: Int) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 3.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                if (lable == "بیت کوین") {
                    Text(
                        text = "دلار",
                        style = h3,
                        color = Color.DarkGray
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.toman), contentDescription = "",
                        Modifier.size(22.dp),
                        tint = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format("%,d", price),
                    style = h2,
                    color = Color.DarkGray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = lable,
                    color = Color.Black,
                    style = h2,
                )
                Spacer(modifier = Modifier.width(8.dp))
                val icon = when (lable) {
                    "دلار" -> R.drawable.ic_dolar
                    "درهم" -> R.drawable.ic_derham
                    "پوند" -> R.drawable.ic_pond
                    "یورو" -> R.drawable.ic_uro
                    "طلا 18عیار" -> R.drawable.ic_18
                    "طلا 24 عیار" -> R.drawable.ic_24
                    "تتر" -> R.drawable.ic_usd
                    "بیت کوین" -> R.drawable.ic_btc
                    else -> R.drawable.ic_24
                }
                Image(
                    painter = painterResource(icon),
                    contentDescription = "",
                    Modifier.size(40.dp)
                )
            }
        }
    }
}

val showDialog = mutableStateOf(false)

@Composable
fun SendComment() {
    var text by remember {
        mutableStateOf("")
    }
    var senMessage by remember {
        mutableStateOf(false)
    }
    if (senMessage) {
        val scope = rememberCoroutineScope()
        LaunchedEffect(true) {
            scope.launch(Dispatchers.IO) {
                val responce = try {
                    SendMessage.api.sendMessageToTelegram(
                        "ExWIdNbkufZaoeBdCjaAZhU190Y2ti9SrqtZuEwr",
                        text
                    )
                } catch (e: Exception) {
                    Log.e("pasi", "error is send")
                    return@launch
                }
                if (responce.isSuccessful) {
                    showDialog.value = false
                    text=""
                }
                withContext(Dispatchers.Main) {
                    senMessage = false
                }
            }

        }
    }

    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                Button(
                    onClick = { senMessage = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF23AF29),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "ارسال پیام",
                        style = h2
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = { text = it },
                        maxLines = 20,
                        textStyle = TextStyle(
                            textAlign = TextAlign.End,
                            textDirection = TextDirection.Ltr,
                            fontFamily = font_standard
                        ),
                        placeholder = {
                            Text(
                                text = "پیام خود را وارد کنید",
                                style = h3,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        label = {
                            Text(
                                text = "پیام",
                                modifier = Modifier.fillMaxWidth(),
                                style = h3,
                                textAlign = TextAlign.End
                            )
                        }
                    )
                }
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = "ارسال پیام به توسعه دهنده",
                    style = h3
                )
            },
        )
    }
}



