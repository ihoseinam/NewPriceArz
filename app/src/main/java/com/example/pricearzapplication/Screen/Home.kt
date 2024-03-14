package com.example.pricearzapplication.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pricearzapplication.Gold.ApiClient
import com.example.pricearzapplication.R
import com.example.pricearzapplication.DataMode.GoldData.Cryptocurrency
import com.example.pricearzapplication.DataMode.GoldData.Currency
import com.example.pricearzapplication.DataMode.GoldData.Gold
import com.example.pricearzapplication.Time.TimeClient
import com.example.pricearzapplication.ui.theme.font_medium
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

    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            val responce = try {
                ApiClient.api.getAllItem()
            } catch (e: Exception) {
                Log.e("pasi", "erorr")
                return@launch
            }
            if (responce.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Crypto.value = responce.body()!!.data.cryptocurrencies
                    Arz.value = responce.body()!!.data.currencies
                    Gold.value = responce.body()!!.data.golds
                    time.value = responce.body()!!.message
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Color(0xFF293DF6)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = TxtDate,
                style = h1,
                color = Color.White,
                textAlign = TextAlign.Center,
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
                .padding(horizontal = 20.dp)
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
            ItemProduct(lable = item.label, price = item.price)
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
            Text(
                text =String.format("%,d", price),
                style = h2,
                color = Color.DarkGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lable,
                    color = Color.Black,
                    style = h2
                )
                Spacer(modifier = Modifier.width(8.dp))
                val icon = when (lable) {
                    "دلار" -> R.drawable.ic_dolar
                    "درهم" -> R.drawable.ic_derham
                    "پوند" -> R.drawable.ic_pond
                    "یورو" -> R.drawable.ic_uro
                    "طلا 18 عیار" -> R.drawable.ic_18
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