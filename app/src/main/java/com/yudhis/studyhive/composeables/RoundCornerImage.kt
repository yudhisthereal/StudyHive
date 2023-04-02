package com.yudhis.studyhive.composeables

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yudhis.studyhive.tools.randomColor

@Composable
fun RoundCornerImage(
    painter: Painter,
    description: String,
    topStart: Dp = 4.dp,
    topEnd: Dp = 4.dp,
    bottomStart: Dp = 4.dp,
    bottomEnd: Dp = 4.dp,
    elevation: Dp = 2.dp,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = description,
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(
                    topStart = topStart,
                    topEnd = topEnd,
                    bottomStart = bottomStart,
                    bottomEnd = bottomEnd
                ),
                clip = true
            ),
        contentScale = contentScale,
        alignment = Alignment.Center,
        colorFilter = ColorFilter.tint(
            color = randomColor(),
            blendMode = BlendMode.Multiply
        )
    )
}

@Composable
fun RoundCornerImage(
    painter: Painter,
    description: String,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 2.dp,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = description,
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(
                    size = cornerRadius
                ),
                clip = true
            ),
        contentScale = contentScale,
        alignment = Alignment.Center,
        colorFilter = ColorFilter.tint(
            color = randomColor(),
            blendMode = BlendMode.Multiply
        )
    )
}