package com.yudhis.studyhive.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.ui.theme.SemiTransparent
import com.yudhis.studyhive.ui.theme.Teal900

@Composable
fun CategoryTag(text:String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SemiTransparent)
            .border(width = 1.dp, color = MaterialTheme.colors.onSurface, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .wrapContentSize(),

    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}