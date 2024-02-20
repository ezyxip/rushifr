package com.ezyxip.runa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ezyxip.runa.R
import com.ezyxip.runa.data.DataAdapter
import com.ezyxip.runa.ui.components.H1

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    goToMain: () -> Unit
){
    if(!DataAdapter.bean.isPasswordSet()) goToMain()
    var passwordValue by remember { mutableStateOf("") }
    var isLoginFailed by remember { mutableStateOf(false) }
    val color = if(isLoginFailed){
        OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Red,
            focusedBorderColor = Color.Red,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White
        )
    } else {
        OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color.White
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(painter = painterResource(id = R.drawable.gerb), contentDescription = null)
        H1(text = "Авторизация", style = TextStyle(Color.White))
        OutlinedTextField(
            modifier = modifier.padding(20.dp),
            value = passwordValue,
            onValueChange = {passwordValue = it},
            label = { Text(text = "Пароль")},
            colors = color,
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                color = Color.White
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            if(DataAdapter.bean.checkPassword(passwordValue)){
                goToMain()
            } else {
                isLoginFailed = true
            }
        }) {
            Text(text = "Войти")
        }
    }
}

