package com.yudhis.studyhive.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.dataclass.MenuItem

@Composable
fun DrawerBody(
    items : List<MenuItem>,
    modifier : Modifier,
    itemTextStyle : TextStyle,
    onItemClick : (MenuItem) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(24.dp)
    ) {
        Text(
            text = "Menu",
            fontWeight = FontWeight.Black,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.width(16.dp))

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
        ) {
            items(items = items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onItemClick(item)
                        }
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.description,
                        tint = contentColorFor(backgroundColor = MaterialTheme.colors.background)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.text,
                        style = itemTextStyle,
                        modifier = Modifier
                            .weight(1f),
                        fontSize = 18.sp,
                        color = contentColorFor(backgroundColor = MaterialTheme.colors.background)
                    )
                }
            }
        }
    }
}