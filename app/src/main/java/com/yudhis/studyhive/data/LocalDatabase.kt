package com.yudhis.studyhive.data

import com.yudhis.studyhive.dataclass.Course
import com.yudhis.studyhive.dataclass.CourseCategory

var coursesDataset: MutableList<Course> = mutableListOf()

fun filteredDataByCategory(category: CourseCategory) : List<Course> {
    val filteredData = coursesDataset.filter {
        it.category == category
    }
    return filteredData
}

fun filteredDataByQuery(query: String) : List<Course> {
    val filteredData = coursesDataset.filter {
        it.title.contains(query.trim(), ignoreCase = true)
    }
    return filteredData
}
