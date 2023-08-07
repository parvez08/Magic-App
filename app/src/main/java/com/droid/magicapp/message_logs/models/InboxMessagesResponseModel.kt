package com.droid.magicapp.message_logs.models

data class InboxMessagesResponse(
    val inboxMessages: List<InboxMessage>
)

data class InboxMessage(
    val address: String,
    val body: String,
    val date: Long
)
