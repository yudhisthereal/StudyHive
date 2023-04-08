package com.yudhis.studyhive.composeables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.dataclass.Course

@Composable
fun HistoryEntry(courseData: Course) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RoundCornerImage(
            painter = courseData.image,
            description = "Course Entry titled ${courseData.title}",
            topStart = 24.dp,
            tint = courseData.tint
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Title
                Text(
                    text = courseData.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Description
                Text(
                    text = courseData.briefDescription,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onBackground
                )
            }

            Column {
                CategoryTag(category = courseData.category)
            }
        }
    }
}