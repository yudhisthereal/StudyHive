package com.yudhis.studyhive.dataclass

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.yudhis.studyhive.ui.theme.*

data class Course(
    val title : String,
    val fullDescription: String,
    val briefDescription: String,
    val image: Painter,
    val rating: Float = 5f, // out of 5
    val prerequisites: List<String> = listOf(),
    val category: CourseCategory = CourseCategory.FrontEnd,
    val startDate: String = "",
    val endDate: String = "",
    val tint: Color
)

enum class CourseCategory {
    FrontEnd, BackEnd, FullStack, MachineLearning, ArtificialIntelligence, CyberSecurity
}

data class TagColor(
    val border: Color,
    val background: Color
)

val courseCategoryColors = mapOf<CourseCategory, TagColor>(
    CourseCategory.FrontEnd to TagColor(
        border = Cyan900,
        background = Cyan50.copy(alpha = 0.15f)
    ),
    CourseCategory.BackEnd to TagColor(
        border = Pink900,
        background = Pink50.copy(alpha = 0.15f)
    ),
    CourseCategory.MachineLearning to TagColor(
        border = Blue900,
        background = LightBlue50.copy(alpha = 0.15f)
    ),
    CourseCategory.FullStack to TagColor(
        border = Red900,
        background = Red50.copy(alpha = 0.15f)
    ),
    CourseCategory.ArtificialIntelligence to TagColor(
        border = Green900,
        background = Green50.copy(alpha = 0.15f)
    ),
    CourseCategory.CyberSecurity to TagColor(
        border = Purple900,
        background = Purple50.copy(alpha = 0.15f)
    )
)

