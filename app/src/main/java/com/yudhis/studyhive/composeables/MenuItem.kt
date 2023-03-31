package com.yudhis.studyhive.composeables

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val id : String,
    val text : String,
    val description : String,
    val icon : ImageVector
)
