package com.ezyxip.runa.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ezyxip.runa.R
import com.ezyxip.runa.service.Decoder
import com.ezyxip.runa.service.Encoder
import com.ezyxip.runa.ui.components.H1
import java.io.File
import java.util.Locale
import java.util.logging.Logger

private val logger = Logger.getLogger("MainScreen")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit,
    goToAuth: () -> Unit
){
    val context = LocalContext.current

    Scaffold (
        topBar = { 
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            modifier = modifier.width(50.dp),
                            painter = painterResource(id = R.drawable.gerb),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.padding(10.dp))
                        Text(text = "РосШифр")
                    }
                },
                actions = {
                    IconButton(onClick = goToAuth) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = goToSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddings ->
        Column (
            modifier = modifier
                .padding(paddings)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            H1(text = "Основной текст")
            var painText by remember { mutableStateOf("") }
            var encodedText by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = modifier
                    .height(150.dp)
                    .fillMaxWidth(0.8f)
                    .padding(10.dp),
                value = painText,
                onValueChange = {painText = it},
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            )
            Column (
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = modifier.padding(10.dp),
                    onClick = { encodedText = Encoder.bean.encode(painText) }
                ) {
                    Text(text = "Шифрование")
                }
                Button(onClick = {
                    val buf = painText
                    painText = encodedText
                    encodedText = buf
                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Button(
                    modifier = modifier.padding(10.dp),
                    onClick = { encodedText = Decoder.bean.decode(painText) }
                ) {
                    Text(text = "Дешифровка")
                }
            }
            OutlinedTextField(
                modifier = modifier
                    .height(150.dp)
                    .fillMaxWidth(0.8f)
                    .padding(10.dp),
                value = encodedText,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            )
            var recorderFlag by remember{ mutableStateOf(false) }
            var paddingForMicrophone by remember { mutableIntStateOf(0) }

            IconButton(
                modifier = modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(if (recorderFlag) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background),
                onClick = {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            1
                        )
                    }
                    val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
                    val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                    val speechRecognizerIntent = Intent(recognizerIntent)
                    val speechRecognizerCallback = object : RecognitionListener{
                        override fun onReadyForSpeech(params: Bundle?) {
                            recorderFlag = true
                        }

                        override fun onBeginningOfSpeech() {

                        }

                        override fun onRmsChanged(rmsdB: Float) {
                            paddingForMicrophone = (paddingForMicrophone + 1) % 10
                        }

                        override fun onBufferReceived(buffer: ByteArray?) {

                        }

                        override fun onEndOfSpeech() {

                        }

                        override fun onError(error: Int) {
                            when (error) {
                                SpeechRecognizer.ERROR_NO_MATCH -> {
                                    painText = "Нет результатов"
                                }
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                                    painText = "Сервис занят"
                                }
                                SpeechRecognizer.ERROR_NETWORK -> {
                                    painText = "Нет интернета"
                                }
                                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                                    painText = "Превышен лимит ожидания"
                                }
                                SpeechRecognizer.ERROR_SERVER -> {
                                    painText = "Ошибка на стороне сервера"
                                }
                                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                                    painText = "Нет ввода"
                                }
                                else -> {
                                    painText = error.toString()
                                }
                            }
                            recorderFlag = false
                        }


                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            painText = matches?.get(0) ?: "No recognition result"
                            recorderFlag = false
                        }

                        override fun onPartialResults(partialResults: Bundle?) {

                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {

                        }

                    }
                    speechRecognizer.setRecognitionListener(speechRecognizerCallback)
                    speechRecognizer.startListening(speechRecognizerIntent)

                }
            ) {
                if(!recorderFlag) paddingForMicrophone = 0
                Icon(
                    modifier = modifier
                        .padding(3.dp)
                        .padding(paddingForMicrophone.dp),
                    painter = painterResource(id = R.drawable.microphone),
                    contentDescription = null,
                    tint = if(recorderFlag) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}