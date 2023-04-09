package com.yudhis.studyhive.composeables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yudhis.studyhive.dataclass.Course

@Composable
fun CourseEntry(courseData: Course, onEnrollClicked: (id:String) -> Unit) {
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
            modifier = Modifier.clickable { onEnrollClicked(courseData.title) },
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
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Description
                Text(
                    text = courseData.briefDescription,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column {
                CategoryTag(category = courseData.category)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        onEnrollClicked(courseData.title)
                    },
                    modifier = Modifier
                        .width(65.dp)
                        .height(33.dp)
                ) {
                    Text(
                        text = "Daftar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

//@Composable
//fun EventEntry(eventData: Event, onEnrollClicked: (eventId: String) -> Unit) {
//    Row(
//        modifier = Modifier
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        RoundCornerImage(
//            painter = eventData.Image,
//            description = "Event Entry titled ${eventData.title}",
//            topStart = 32.dp
//        )
//        Column {
//            // Title
//            Text(
//                text = eventData.title,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold
//            )
//            // Description
//            Text(
//                text = eventData.briefDescription,
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Light
//            )
//            //Enrollment due date
//            Text(text = "Pendaftaran ditutup pada:")
//            Text(text = eventData.enrollDueDate.toString())
//            // Meta and Enroll Button
//            Row {
//                Text(text = eventData.category.toString())
//                Button(
//                    onClick = {
//                        onEnrollClicked(eventData.title)
//                    }
//                ) {
//                    Text(
//                        text = "Daftar",
//                        fontWeight =  FontWeight.Medium,
//                        fontSize = 16.sp
//                    )
//                }
//            }
//        }
//    }
//}
//
