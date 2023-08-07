package com.droid.magicapp.call_logs.utils

object CallDurationUtils {
    fun formatCallDuration(callDurationInSeconds: Long): String {
        val hours = callDurationInSeconds / 3600
        val minutes = (callDurationInSeconds % 3600) / 60
        val seconds = callDurationInSeconds % 60

        return when {
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format("%02d:%02d", minutes, seconds)
            else -> String.format("%02d sec", seconds)
        }
    }
}