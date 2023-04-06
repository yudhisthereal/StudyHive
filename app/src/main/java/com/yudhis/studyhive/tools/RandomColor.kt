package com.yudhis.studyhive.tools

import androidx.compose.ui.graphics.Color
import com.yudhis.studyhive.ui.theme.*
import java.util.Random

private var lastPickedColor: Color = Color.Magenta

fun randomColor() : Color {
    var color : Color = Color.Magenta
    val rnd = Random()
    when (rnd.nextInt(5)) {
        0 -> color = Red300
        1 -> color = Teal300
        2 -> color = Yellow300
        3 -> color = Pink500
        4 -> color = Orange300
        5 -> color = Gray300
    }
    while (color == lastPickedColor) {
        when (rnd.nextInt(3)) {
            0 -> color = Red300
            1 -> color = Teal300
            2 -> color = Yellow300
            3 -> color = Pink500
            4 -> color = Orange300
            5 -> color = Gray300
        }
    }
    lastPickedColor = color
    return color
}