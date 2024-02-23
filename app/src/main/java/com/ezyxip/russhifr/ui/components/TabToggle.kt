package com.ezyxip.russhifr.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TabToggle(
    modifier: Modifier = Modifier,
    currentItem: TabTogglePoints = TabTogglePoints.First,
    changeCurrentItem: (TabTogglePoints) -> Unit,
    firstValue: String,
    secondValue: String
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ){
        TabToggleItem(
            modifier = modifier,
            value = firstValue,
            isSelected = currentItem == TabTogglePoints.First,
            onclick = {changeCurrentItem(TabTogglePoints.First)}
        )
        TabToggleItem(
            modifier = modifier,
            value = secondValue,
            isSelected = currentItem == TabTogglePoints.Second,
            onclick = {changeCurrentItem(TabTogglePoints.Second)}
        )
    }
}

enum class TabTogglePoints{
    First, Second
}

@Composable
fun TabToggleItem(
    modifier: Modifier = Modifier,
    value: String,
    isSelected: Boolean,
    onclick: () -> Unit = {}
){
    Column (
        modifier = modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = modifier
                .clickable { onclick() }
                .padding(10.dp, 5.dp),
            text = value,
            fontSize = 20.sp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
        if(isSelected)
            Divider(modifier.width(100.dp).padding(5.dp))
    }

}
