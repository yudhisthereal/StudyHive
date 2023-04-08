package com.yudhis.studyhive.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.dataclass.MenuItem

@Composable
fun DrawerHeader(
    profilePic: ImageVector,
    profileName: String,
    profileEmail: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(216.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        // Profile Pic
        Image(
            imageVector = profilePic,
            contentDescription = "Your Profile Picture",
            modifier = Modifier
                .width(86.dp)
                .height(86.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
        )
        Spacer(Modifier.height(16.dp))
        // Profile Name
        Text(
            text = profileName,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(8.dp))
        // Profile Email
        Text(
            text = profileEmail,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.75f)
        )
    }
}

@Composable
fun HorizontalDivider(bgColor: Color) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(bgColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        Divider(
            modifier = Modifier.width(214.dp),
            thickness = 2.dp,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.75f)
        )
        Spacer(Modifier.height(16.dp))
    }
}
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