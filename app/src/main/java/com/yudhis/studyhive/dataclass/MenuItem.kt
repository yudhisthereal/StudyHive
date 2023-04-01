package com.yudhis.studyhive.dataclass

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id : String,
    val text : String,
    val description : String,
    val icon : ImageVector
)
