package com.swancodes.notes.ui.home

import com.swancodes.notes.R
import kotlin.random.Random

fun randomColor(): Int {
    val backgroundColors = ArrayList<Int>()
    backgroundColors.add(R.color.pink_500)
    backgroundColors.add(R.color.yellow)
    backgroundColors.add(R.color.green)
    backgroundColors.add(R.color.pink)
    backgroundColors.add(R.color.teal)
    backgroundColors.add(R.color.purple)

    val seed = System.currentTimeMillis().toInt()
    val randomIndex = Random(seed).nextInt(backgroundColors.size)
    return backgroundColors[randomIndex]
}