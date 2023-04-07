package com.yudhis.studyhive.dataclass

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector

data class Participant(
    val pName: String,
    val pNickName: String,
    val pId: String,
    val pPic: ImageVector,
    val address: String,
    val birthdate: String,
    var sktm: ImageVector,
    var skd: ImageVector,
    var course_history: List<Course>
)
