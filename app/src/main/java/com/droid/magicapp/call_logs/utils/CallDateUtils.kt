package com.droid.magicapp.call_logs.utils

import java.text.SimpleDateFormat
import java.util.*

object CallDateUtils {
    fun formatCallDate(callDateTimestamp: Long): String {
        val callDate = Date(callDateTimestamp)
        val format = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale.getDefault())
        return format.format(callDate)
    }
}
