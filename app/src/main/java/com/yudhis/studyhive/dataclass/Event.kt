package com.yudhis.studyhive.dataclass

import androidx.compose.ui.graphics.painter.Painter

data class Event(
    val title : String,
    val fullDescription: String,
    val briefDescription: String,
    val requirements: List<String>,
    val Image: Painter,
    val category: EventCategory,
    val enrollDueDate: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

enum class EventCategory {
    SocialEvent, Seminar, Webinar, Workshop, Competition
}
