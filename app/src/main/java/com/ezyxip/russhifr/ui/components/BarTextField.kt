package com.ezyxip.russhifr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    colors: BarTextFieldColors = MaterialTheme.optionTextFieldColors(),
    bottomBar: @Composable RowScope.() -> Unit = {}
){
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = modifier
            .border(1.dp, colors.borderColor, shape = RoundedCornerShape(5.dp))
            .background(colors.bgColor)
            .clickable { focusRequester.requestFocus() }
    ){
        BasicTextField(
            modifier = modifier
                .padding(10.dp)
                .focusRequester(focusRequester),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            minLines = minLines
        )
        Row (
            modifier,
            horizontalArrangement = Arrangement.End
        ) {
            bottomBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BarTextFieldPreview(){
    var value by remember { mutableStateOf("") }
    Column (
    ){
        BarTextField(
            value = value,
            onValueChange = { value = it },
            bottomBar = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = null)
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
                }
            },
            textStyle = TextStyle(fontSize = 20.sp)
        )
    }
}

data class BarTextFieldColors(
    var bgColor: Color,
    var borderColor: Color
)

@Composable
fun MaterialTheme.optionTextFieldColors(): BarTextFieldColors{
    return BarTextFieldColors(
        bgColor = colorScheme.background,
        borderColor = colorScheme.primary
    )
}