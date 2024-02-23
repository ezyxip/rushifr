package com.ezyxip.russhifr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ezyxip.russhifr.data.DataAdapter
import com.ezyxip.russhifr.ui.components.SettingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    goToMain: () -> Unit,
    goToPasswordManager: () -> Unit,
    goToDictManager: () -> Unit
){
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Настройки") },
                navigationIcon = {
                    IconButton(onClick = goToMain) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){ paddings ->
        Column (
            modifier = modifier.padding(paddings)
        ){
            var flag by remember {
                mutableStateOf(false)
            }
            if(flag){
                AlertDialog(
                    onDismissRequest = {flag = false},
                    title = { Text(text = "Подтверждение")},
                    text = { Text(text = "Вы действительно хотите оочистить словарь", color = MaterialTheme.colorScheme.onSurface)},
                    confirmButton = {
                        Button(
                            onClick = {
                                flag = false
                                DataAdapter.bean.setDictionary(mapOf())
                            }
                        ) {
                            Text(text = "Очистить")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                flag = false
                            }
                        ) {
                            Text(text = "Отменить")
                        }
                    }
                )
            }
            SettingItem(name = "Пароль", label = "Установить или изменить пароль"){goToPasswordManager()}
            SettingItem(name = "Словарь", label = "Настроить словарь шифрования"){goToDictManager()}
            SettingItem(name = "Очистить словарь", label = "Очистить словарь шифрования"){flag = true}
        }
    }
}