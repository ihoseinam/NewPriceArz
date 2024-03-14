package com.example.pricearzapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pricearzapplication.navigation.SetUpNAvGraph

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("pasi","start")
        setContent {
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


        }
    }

}
