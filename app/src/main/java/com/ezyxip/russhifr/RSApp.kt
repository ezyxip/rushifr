package com.ezyxip.russhifr

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezyxip.russhifr.ui.screens.AuthScreen
import com.ezyxip.russhifr.ui.screens.MainScreen
import com.ezyxip.russhifr.ui.screens.SettingScreen

@Composable
fun RSApp(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val navGraph = navController.createGraph("/auth"){
        composable(route = "/main"){
            MainScreen(modifier) {navController.navigate("/settings")}
        }
        composable(route = "/settings"){
            SettingScreen(modifier) {navController.popBackStack()}
        }
        composable(route = "/auth"){
            AuthScreen(modifier) {navController.navigate("/main")}
        }
    }

    NavHost(navController = navController, graph = navGraph)
}

