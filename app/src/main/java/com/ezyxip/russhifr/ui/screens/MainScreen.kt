package com.ezyxip.russhifr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.ezyxip.russhifr.service.Encoder
import com.ezyxip.russhifr.ui.components.H1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit
){
    Scaffold (
        topBar = { 
            TopAppBar(
                title = { Text(text = "РосШифр")},
                actions = {
                    IconButton(onClick = goToSettings) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) { paddings ->
        Column (
            modifier = modifier
                .padding(paddings)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            H1(text = "Основной текст")
            var painText by remember { mutableStateOf("") }
            var encodedText by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = modifier
                    .height(200.dp)
                    .padding(10.dp),
                value = painText,
                onValueChange = {painText = it}
            )
            Button(
                modifier = modifier.padding(10.dp),
                onClick = { encodedText = Encoder.bean.encode(painText) }
            ) {
                Text(text = "Шифрование")
            }
            OutlinedTextField(
                modifier = modifier
                    .height(200.dp)
                    .padding(10.dp),
                value = encodedText,
                onValueChange = {},
                readOnly = true
            )
            IconButton(
                modifier = modifier.padding(10.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }
}