package com.yudhis.studyhive.dataclass

import androidx.compose.ui.graphics.painter.Painter

data class Course(
    val title : String,
    val fullDescription: String,
    val briefDescription: String,
    val image: Painter,
    val rating: Float = 5f, // out of 5
    val prerequisites: List<String> = listOf(),
    val category: CourseCategory = CourseCategory.FrontEnd,
    val startDate: String = "",
    val endDate: String = ""
)

enum class CourseCategory {
    FrontEnd, BackEnd, FullStack, MachineLearning, ArtificialIntelligence, CyberSecurity
}

