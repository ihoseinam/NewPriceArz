package com.example.pricearzapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pricearzapplication.data.Currency
import com.example.pricearzapplication.navigation.SetUpNAvGraph
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ee by remember {
                gold
            }
            navController = rememberNavController()
            Scaffold(
                bottomBar = {},
                topBar = {},
                ) {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    SetUpNAvGraph(navController =navController )
                }
            }

//            Text(text = "star")
//            runBlocking {
//                getAllGold()
//            }
//            Log.e("pasi",ee.toString())
//            LazyColumn(){
//                itemsIndexed(ee){ _, item ->
//                    item(name = item.label, price = item.price.toString(), sym = item.symbol)
//                }
//            }
        }
    }

    var gold = mutableStateOf(emptyList<Currency>())
    suspend fun getAllGold() {
        val responce = ApiClient.api.getAllItem()
        if (responce.isSuccessful) {
            responce.body().let {
                gold.value = it?.data?.currencies!!
                Log.e("pasi", it.data.golds.toString())
            }
        }
    }

    @Composable
    fun item(name: String, price: String, sym: String) {
        Card(
            elevation = CardDefaults.cardElevation(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = price)
                Row {
                    Text(text = sym)
                    Text(text = name)

                }

            }
        }
    }
}
