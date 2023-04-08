package com.yudhis.studyhive.dataclass

import androidx.compose.ui.graphics.vector.ImageVector

data class Participant(
    var pName: String,
    var pNickName: String,
    val pId: String,
    var pPic: ImageVector,
    var pAddress: String,
    var pBirthdate: String,
    var sktm: ImageVector,
    var skd: ImageVector,
    var courseHistory: MutableList<Course>
)
