package com.example.pricearzapplication.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pricearzapplication.ApiClient
import com.example.pricearzapplication.R
import com.example.pricearzapplication.data.Cryptocurrency
import com.example.pricearzapplication.data.Currency
import com.example.pricearzapplication.data.Gold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var Crypto = mutableStateOf(emptyList<Cryptocurrency>())
var Gold = mutableStateOf(emptyList<Gold>())
var Arz = mutableStateOf(emptyList<Currency>())

@Composable
fun HomeScree() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        scope.launch {
            val responce = try {
                ApiClient.api.getAllItem()
            } catch (e: Exception) {
                Log.e("pasi", "erorr")
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (responce.isSuccessful) {
                Log.e("pasi", responce.body()!!.message)
                withContext(Dispatchers.Main) {
                    Crypto.value = responce.body()!!.data.cryptocurrencies
                    Arz.value = responce.body()!!.data.currencies
                    Gold.value = responce.body()!!.data.golds
                }
            }

        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3.0f)
                .background(Color.Blue)
        ) {
            Text(text = "sfhk")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(7.0f)
        ) {
            TabHome()
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHome() {
    val item = listOf(
        Tab(
            "crypro",
            Icons.Rounded.Info,
            Icons.Outlined.Info,
        ),
        Tab(
            "Arz",
            Icons.Rounded.Home,
            Icons.Outlined.Home,
        ),
        Tab(
            "gold",
            Icons.Rounded.ShoppingCart,
            Icons.Outlined.ShoppingCart,
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
        TabRow(selectedTabIndex = selectedTabIndex) {
            item.forEachIndexed { index, tab ->
                Tab(selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(text = tab.name)
                    },
                    icon = {
                        if (selectedTabIndex == index) {
                            Icon(tab.selectedIcon, contentDescription = "")
                        } else {
                            Icon(tab.unselectedIcon, contentDescription = "")
                        }
                    }
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
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 3.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = price.toString())
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = lable)
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