package com.yudhis.studyhive.dataclass

import android.media.Image

data class Participant(
    val pname: String,
    val pid: String,
    val address: String,
    val birthdate: String,
    var sktm: Image,
    var skd: Image,
    var course_history: List<Course>
)
