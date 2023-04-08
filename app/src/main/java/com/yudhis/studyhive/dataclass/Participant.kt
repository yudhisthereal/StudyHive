package com.yudhis.studyhive.dataclass

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector

data class Participant(
    var pName: String,
    var pNickName: String,
    val pId: String,
    var pPic: ImageVector,
    var address: String,
    var birthdate: String,
    var sktm: ImageVector,
    var skd: ImageVector,
    var course_history: List<Course>
)
