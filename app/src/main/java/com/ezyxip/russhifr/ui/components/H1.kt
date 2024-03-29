package com.ezyxip.russhifr.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun H1(
    modifier: Modifier = Modifier,
    text: String = "Title",
    style: TextStyle = TextStyle()
){
    Text(
        modifier = modifier
            .padding(15.dp, 10.dp),
        text = text,
        fontSize = 30.sp,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun H1Preview(){
    H1(text = "Проверка")
}