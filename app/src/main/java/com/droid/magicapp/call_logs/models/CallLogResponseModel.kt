package com.droid.magicapp.call_logs.models

data class CallLogResponseModel(
    val callLogs: List<CallLogEntry>
)

data class CallLogEntry(
    val phoneNumber: String?,
    val callType: Int?,
    val callDate: Long?,
    val callDuration: Long?
)

