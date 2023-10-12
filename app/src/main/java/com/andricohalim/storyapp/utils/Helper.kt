package com.andricohalim.storyapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.withCustomAndFullDateFormat(): String {
    val customFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    val date = customFormat.parse(this) as Date
    val fullDateFormat = SimpleDateFormat.getDateInstance(DateFormat.FULL, Locale.US)
    return fullDateFormat.format(date)
}