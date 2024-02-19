package com.ezyxip.russhifr.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.unit.sp
import com.ezyxip.russhifr.data.DataAdapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictManagerScreen(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit
){
    var rules by remember {
        mutableStateOf(DataAdapter.bean.getDictionary())
    }
    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Словарь") },
                navigationIcon = {
                    IconButton(onClick = goToSettings) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        DataAdapter.bean.setDictionary(rules)
                        goToSettings()
                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                    }
                }
            )
        }
    ){ paddings ->
        var key by remember {
            mutableStateOf("")
        }
        var value by remember {
            mutableStateOf("")
        }
        Column (
            modifier = modifier.fillMaxSize().padding(paddings)
        ){

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row (
                    modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedTextField(
                        modifier = modifier.width(100.dp),
                        value = key,
                        onValueChange = {
                            if(it.length <= 1){
                                key = it
                            }
                        }
                    )
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
                    OutlinedTextField(
                        modifier = modifier.width(100.dp),
                        value = value,
                        onValueChange = {
                            if(it.length <= 1){
                                value = it
                            }
                        }
                    )
                }
                IconButton(onClick = {
                    if(key.length == 1 && value.length == 1){
                        rules = rules + (key[0].lowercaseChar() to value[0].lowercaseChar())
                    }
                }) {
                    Icon(
                        modifier = modifier.padding(10.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
            LazyColumn(modifier = modifier.imePadding()) {
                items(rules.toList()) { rule ->
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = modifier.padding(10.dp),
                                text = rule.first.toString(),
                                fontSize = 20.sp
                            )
                            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
                            Text(
                                modifier = modifier.padding(10.dp),
                                text = rule.second.toString(),
                                fontSize = 20.sp
                            )
                        }
                        IconButton(onClick = { rules = rules.filter { e -> e.toPair() != rule } }) {
                            Icon(
                                modifier = modifier.padding(10.dp),
                                imageVector = Icons.Filled.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}