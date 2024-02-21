package com.ezyxip.runa.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ezyxip.runa.ui.components.SettingItem

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
            SettingItem(name = "Пароль", label = "Установить или изменить пароль"){goToPasswordManager()}
            SettingItem(name = "Словарь", label = "Настроить словарь шифрования"){goToDictManager()}
        }
    }
}