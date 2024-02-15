package com.ezyxip.russhifr.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    name: String,
    label: String,
    onClick: () -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            modifier = modifier.padding(20.dp, 5.dp),
            text = name,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = modifier.padding(20.dp, 5.dp),
            text = label,
            fontSize = 12.sp,

        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingItemPreview(){
    SettingItem(name = "Setting", label = "More info") {}
}