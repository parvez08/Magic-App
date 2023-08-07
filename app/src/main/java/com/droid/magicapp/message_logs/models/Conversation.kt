package com.droid.magicapp.message_logs.models

data class Conversation(
    val sender: String,
    val messages: MutableList<InboxMessage>
)

