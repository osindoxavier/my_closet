package com.closet.xavier.utils

import java.time.LocalTime

fun getDaySalutation(): String {
    val currentTime = LocalTime.now()

    return when {
        currentTime.isBefore(LocalTime.NOON) -> "Good morning!"
        currentTime.isBefore(LocalTime.of(17, 0)) -> "Good afternoon!"
        else -> "Good evening!"
    }
}