package com.example.pricearzapplication.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.example.pricearzapplication.DataMode.GoldData.Cryptocurrency
import com.example.pricearzapplication.DataMode.GoldData.Currency
import com.example.pricearzapplication.DataMode.GoldData.Gold
import com.example.pricearzapplication.R
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
val timeRozf = mutableStateOf("")
val timeRoza = mutableStateOf("")
val timeMah = mutableStateOf("")
val timeSal = mutableStateOf("")

@Composable
fun HomeScree() {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFFFFF))
                    .padding(horizontal = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { showDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = " ", tint = Color(0xFF8D2222)
                    )
                }
                Text(
                    text = "قیمت لحظه ای انوع ارز",
                    style = h1,
                    color = Color.DarkGray
                )

            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFFFFFFFF))
        ) {
            SendComment(snackbarHostState)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .background(Color(0xFFF5F4F2)),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(vertical = 10.dp, horizontal = 8.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFFFFFFFF)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val item = listOf<String>(
                            timeSal.value,
                            timeMah.value,
                            timeRoza.value,
                            timeRozf.value,
                        )
                        for (items in item) {
                            ItemDate(text = items)
                        }

                    }

                }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            indicator = {},
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xffF5F4F8),
        ) {
            item.forEachIndexed { index, tab ->
                Tab(
                    modifier = Modifier
                        .padding(bottom = 9.dp, start = 4.dp, end = 4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (selectedTabIndex == index)
                                Color(0xFF8D2222)
                            else Color.White
                        ),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Black,
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
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = time.value,
                        textAlign = TextAlign.End,
                        fontFamily = font_standard,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_update),
                        contentDescription = "",
                        Modifier.size(30.dp),
                        tint = Color.DarkGray,
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                when (it) {
                    0 -> Crypto(cr)
                    1 -> Gold(tala)
                    2 -> Currency(arz)
                }
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 13.dp)
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
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
                val e = if (lable == "تتر") {
                    price / 10
                } else {
                    price
                }
                Text(
                    text = String.format("%,d", e),
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
                    "تتر" -> R.drawable.ic_usdt
                    "بیت کوین" -> R.drawable.ic_btc
                    else -> R.drawable.ic_gold
                }
                Image(
                    painter = painterResource(icon),
                    contentDescription = "",
                    Modifier.size(40.dp)
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.4f)
                .padding(horizontal = 12.dp),
            thickness = 0.2.dp,
            color = Color.DarkGray
        )
    }

}


val showDialog = mutableStateOf(false)

@Composable
fun SendComment(snackbarHostState: SnackbarHostState) {
    var text by remember {
        mutableStateOf("")
    }
    var senMessage by remember {
        mutableStateOf(false)
    }
    var Loading by remember {
        mutableStateOf(false)
    }
    if (senMessage) {
        val scope = rememberCoroutineScope()
        LaunchedEffect(senMessage) {
            scope.launch(Dispatchers.IO) {
                val responce = try {
                    SendMessage.api.sendMessageToTelegram(
                        "ExWIdNbkufZaoeBdCjaAZhU190Y2ti9SrqtZuEwr",
                        "new price arz message :\n $text"

                    )
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        snackbarHostState.showSnackbar(
                            message = e.message.toString(),
                            actionLabel = "Close"
                        )
                    }
                    return@launch
                }
                if (responce.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Loading = false
                        showDialog.value = false
                        text = ""
                        snackbarHostState.showSnackbar(
                            message = responce.body()!!.message,
                            actionLabel = "Close"
                        )
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        snackbarHostState.showSnackbar(
                            message = responce.body()!!.message,
                            actionLabel = "Close"
                        )
                    }
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
            onDismissRequest = {
                showDialog.value = false
                Loading = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        Loading = true
                        senMessage = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF23AF29),
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(Loading) {
                            Loading3Dot()
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ارسال پیام",
                            style = h2
                        )
                    }

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

@Composable
fun ItemDate(text: String) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            Color(0xFFFFFFFF)
        ),
        modifier = Modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation(12.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp)
                .padding(vertical = 5.dp, horizontal = 14.dp),
            text = text,
            style = h2,
            color = Color.Black,
        )
    }
}



