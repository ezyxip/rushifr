package com.ezyxip.russhifr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ezyxip.russhifr.data.DataAdapter
import com.ezyxip.russhifr.ui.components.H1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    goToMain: () -> Unit
){
    if(!DataAdapter.defaultAdapter.isPasswordSet()) goToMain()
    var passwordValue by remember { mutableStateOf("") }
    var isLoginFailed by remember { mutableStateOf(false) }
    val color = if(isLoginFailed){
        OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.Red, focusedBorderColor = Color.Red)
    } else {
        OutlinedTextFieldDefaults.colors()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        H1(text = "Авторизация")
        OutlinedTextField(
            modifier = modifier.padding(20.dp),
            value = passwordValue,
            onValueChange = {passwordValue = it},
            label = { Text(text = "Пароль")},
            colors = color
        )
        Button(onClick = {
            if(DataAdapter.defaultAdapter.checkPassword(passwordValue)){
                goToMain()
            } else {
                isLoginFailed = true
            }
        }) {
            Text(text = "Войти")
        }
    }
}