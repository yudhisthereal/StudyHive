package com.yudhis.studyhive.tools

import androidx.compose.ui.graphics.Color
import java.util.Random

private var lastPickedColor: Color = Color.Magenta

fun randomColor() : Color {
    var color : Color = Color.Magenta
    val rnd = Random()
    when (rnd.nextInt(3)) {
        0 -> color = Color.Red
        1 -> color = Color.Green
        2 -> color = Color.Yellow
    }
    while (color == lastPickedColor) {
        when (rnd.nextInt(3)) {
            0 -> color = Color.Red
            1 -> color = Color.Green
            2 -> color = Color.Yellow
        }
    }
    lastPickedColor = color
    return color
}