package com.ezyxip.russhifr.ui.screens

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.core.content.getSystemService
import com.ezyxip.russhifr.R
import com.ezyxip.russhifr.service.Decoder
import com.ezyxip.russhifr.service.Encoder
import com.ezyxip.russhifr.ui.components.BarTextField
import com.ezyxip.russhifr.ui.components.H1
import com.ezyxip.russhifr.ui.components.TabToggle
import com.ezyxip.russhifr.ui.components.TabTogglePoints
import java.util.Locale

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
            var plainText by remember { mutableStateOf("") }
            var encodedText by remember { mutableStateOf("") }
            var isEncode by remember {
                mutableStateOf(TabTogglePoints.First)
            }
            val action = if (isEncode == TabTogglePoints.First){
                { encodedText = Encoder.bean.encode(plainText)}
            } else {
                {encodedText = Decoder.bean.decode(plainText)}
            }
            action()
            TabToggle(
                modifier,
                currentItem = isEncode,
                changeCurrentItem = {
                    isEncode = it
                },
                firstValue = "Шифрование",
                secondValue = "Дешифровка",
            )
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            BarTextField(
                modifier = modifier.fillMaxWidth(),
                value = plainText,
                onValueChange = {
                    plainText = it
                    action()
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            ){
                IconButton(onClick = {
                    if(clipboard.hasPrimaryClip() && clipboard.primaryClipDescription!!.hasMimeType("text/plain")){
                        plainText = clipboard.primaryClip!!.getItemAt(0).text.toString()
                    }
                }) {
                    Icon(
                        modifier = modifier.width(20.dp),
                        painter = painterResource(id = R.drawable.paste),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                    IconButton(onClick = { plainText = "" }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
            }
            Column (
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = modifier.padding(5.dp),
                    onClick = {
                        val buf = plainText
                        plainText = encodedText
                        encodedText = buf
                    }
                ) {
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
            }
            BarTextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = encodedText,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground)
            ){
                IconButton(onClick = {
                    clipboard.setPrimaryClip(ClipData.newPlainText("Encoded text", encodedText))
                }) {
                    Icon(
                        modifier = modifier.width(20.dp),
                        painter = painterResource(id = R.drawable.copy),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = {
                    val sendIntent: Intent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, encodedText)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
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
                                    plainText = "Нет результатов"
                                }
                                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                                    plainText = "Сервис занят"
                                }
                                SpeechRecognizer.ERROR_NETWORK -> {
                                    plainText = "Нет интернета"
                                }
                                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                                    plainText = "Превышен лимит ожидания"
                                }
                                SpeechRecognizer.ERROR_SERVER -> {
                                    plainText = "Ошибка на стороне сервера"
                                }
                                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                                    plainText = "Нет ввода"
                                }
                                else -> {
                                    plainText = error.toString()
                                }
                            }
                            recorderFlag = false
                        }


                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            plainText = matches?.get(0) ?: "No recognition result"
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