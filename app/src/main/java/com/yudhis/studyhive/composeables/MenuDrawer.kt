package com.yudhis.studyhive.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DrawerHeader() {
    Text(
        text = "Menu",
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight(200)
    )
}

@Composable
fun DrawerBody(
    items : List<MenuItem>,
    modifier : Modifier,
    itemTextStyle : TextStyle,
    onItemClick : (MenuItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.description
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.text,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp
                )
            }
        }
    }
}