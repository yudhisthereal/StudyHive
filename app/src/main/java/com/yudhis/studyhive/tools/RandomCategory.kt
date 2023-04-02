package com.yudhis.studyhive.tools

import com.yudhis.studyhive.dataclass.CourseCategory
import com.yudhis.studyhive.dataclass.EventCategory
import java.util.Random

private var lastPickedCourseCategory: CourseCategory = CourseCategory.FrontEnd
private var lastPickedEventCategory: EventCategory = EventCategory.SocialEvent

fun randomCourseCategory(): CourseCategory {
    var category = CourseCategory.FrontEnd
    val categories = CourseCategory.values()
    while(category == lastPickedCourseCategory) {
        category = categories[Random().nextInt(categories.size)]
    }
    lastPickedCourseCategory = category
    return category
}

fun randomEventCategory(): EventCategory {
    var category = EventCategory.SocialEvent
    val categories = EventCategory.values()
    while(category == lastPickedEventCategory) {
        category = categories[Random().nextInt(categories.size)]
    }
    lastPickedEventCategory = category
    return category
}