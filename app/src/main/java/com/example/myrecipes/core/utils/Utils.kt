package com.example.myrecipes.core.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit

fun String.parseDuration(): Int {
    return try {
        Duration.parse(this).toInt(DurationUnit.MINUTES)
    } catch (e: IllegalArgumentException) {
        -1
    }
}