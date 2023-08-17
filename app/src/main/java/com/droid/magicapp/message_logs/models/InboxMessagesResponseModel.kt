package com.droid.magicapp.message_logs.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InboxMessage(
    val id:String?,
    val address: String?,
    val body: String?,
    val date: Long?,
    val isSent: Boolean?
) : Parcelable
