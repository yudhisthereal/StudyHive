package com.yudhis.studyhive.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.yudhis.studyhive.dataclass.*

@Composable
fun MyCard(
    size: Int,
    cornerRadius: Dp,
    text: String,
    imagePainter: Painter,
    description: String
) {
    val modifier = Modifier
    var width = 4.dp
    var height = 3.dp
    var fontSize = 12.sp
    var padding = 16.dp
    when(size) {
        Small -> {
            width *= 16
            height *= 16
            fontSize = 12.sp
            padding = 8.dp
        }
        Medium -> {
            width *= 32
            height *= 32
            fontSize = 16.sp
            padding = 12.dp
        }
        Large -> {
            width *= 64
            height *= 64
            fontSize = 24.sp
            padding = 16.dp
        }
    }
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .padding(padding)
            .shadow(elevation = 2.dp),
    ) {
        Box(
            modifier =  modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent,
                        MaterialTheme.colors.background
                    ),
                ))
                .zIndex(2f)

        )
        Image(
            painter = imagePainter,
            contentDescription = description,
            modifier = modifier
                .zIndex(1f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(cornerRadius)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = text,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(padding)
                .zIndex(3f),
            fontSize = fontSize,
            fontWeight = FontWeight(600)
        )
    }
}