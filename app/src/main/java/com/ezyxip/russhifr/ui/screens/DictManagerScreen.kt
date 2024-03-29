package com.ezyxip.russhifr.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.ezyxip.russhifr.R
import com.ezyxip.russhifr.data.DataAdapter
import java.io.File
import java.io.InputStreamReader

fun verifyDict(dict: Map<Char, Char>): Boolean{
    return dict.size == dict.values.toSet().size
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictManagerScreen(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit
){
    var rules by remember {
        mutableStateOf(DataAdapter.bean.getDictionary())
    }
    val context = LocalContext.current
    val intent = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        try {
            if (uri == null) throw Exception("Не удаётся получить доступ к файлу")
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw Exception("Не удаётся получить доступ к файлу")
            val dict = mutableMapOf<Char, Char>()
            InputStreamReader(inputStream).forEachLine {
                val splitedLine = it.split("~|~")
                dict[splitedLine[0][0]] = splitedLine[1][0]
            }
            DataAdapter.bean.setDictionary(dict)
            goToSettings()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
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
                        intent.launch("text/*")
                    }) {
                        Icon(
                            modifier = modifier
                                .width(24.dp),
                            painter = painterResource(id = R.drawable.downloads),
                            contentDescription = null, tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = {
                        val file = File(context.filesDir, "dictionary")
                        val uri = FileProvider.getUriForFile(context, "com.ezyxip.russhifr.fileprovider", file)
                        val sendRequest = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, uri)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendRequest, null))
                    }) {
                        Icon(imageVector = Icons.Filled.Share, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                    IconButton(onClick = {
                        if(!verifyDict(rules)){
                            Toast.makeText(context, "Словарь не задаёт однозначной дешифровки", Toast.LENGTH_LONG).show()
                        }
                        DataAdapter.bean.setDictionary(rules)
                        goToSettings()
                    }) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
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
            modifier = modifier
                .fillMaxSize()
                .padding(paddings)
                .padding(5.dp)
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
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                    )
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null)
                    OutlinedTextField(
                        modifier = modifier.width(100.dp),
                        value = value,
                        onValueChange = {
                            if(it.length <= 1){
                                value = it
                            }
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
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