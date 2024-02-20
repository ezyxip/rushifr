package com.ezyxip.runa

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezyxip.runa.data.FileDataStoreConfiguration
import com.ezyxip.runa.ui.screens.AuthScreen
import com.ezyxip.runa.ui.screens.DictManagerScreen
import com.ezyxip.runa.ui.screens.MainScreen
import com.ezyxip.runa.ui.screens.PasswordManagerScreen
import com.ezyxip.runa.ui.screens.SettingScreen
import java.io.File
import java.io.FileWriter

@Composable
fun RSApp(modifier: Modifier = Modifier){
    FileDataStoreConfiguration.bean.appDir = LocalContext.current.filesDir
    val navController = rememberNavController()
    val navGraph = navController.createGraph("/auth"){
        composable(route = "/main"){
            MainScreen(
                modifier,
                goToSettings = {navController.navigate("/settings")},
                goToAuth = {navController.navigate("/auth")}
            )
        }
        composable(route = "/settings"){
            SettingScreen(
                modifier,
                goToMain = {navController.navigate("/main")},
                goToDictManager = {navController.navigate("/dictmanager")},
                goToPasswordManager = {navController.navigate("/passmanager")}
            )
        }
        composable(route = "/auth"){
            BackHandler (true){}
            AuthScreen(modifier) {navController.navigate("/main")}
        }
        composable(route = "/passmanager"){
            PasswordManagerScreen (
                modifier,
                goToSettings = {
//                    navController.navigate("/settings")
                    navController.popBackStack()
                }
            )
        }
        composable(route = "/dictmanager"){
            DictManagerScreen (
                modifier,
                goToSettings = {
//                    navController.navigate("/settings")
                    navController.popBackStack()
                }
            )
        }
    }
    NavHost(navController = navController, graph = navGraph)
}