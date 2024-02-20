package com.ezyxip.runa.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ezyxip.runa.data.DataAdapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit,
){
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Изменение пароля") },
                navigationIcon = {
                    IconButton(onClick = goToSettings) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){ paddings ->
        var passwordValue by remember { mutableStateOf("") }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(paddings),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            OutlinedTextField(
                modifier = modifier.padding(20.dp),
                value = passwordValue,
                onValueChange = {passwordValue = it},
                label = { Text(text = "Новый пароль")},
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
            Button(onClick = {
                DataAdapter.bean.setPassword(passwordValue)
                goToSettings()
            }) {
                Text(text = "Сохранить пароль")
            }
        }
    }
}