package com.droid.magicapp.message_logs.utils


import android.util.Log
import com.droid.magicapp.message_logs.models.InboxMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.koin.java.KoinJavaComponent.inject

class MessageLogsDataBaseHandler {

    private val database: DatabaseReference by inject(DatabaseReference::class.java)

    fun fetchMessagesForNumber(
        targetedPhoneNumber: String,
        callback: (List<InboxMessage>) -> Unit
    ) {
        val messagesReference = database.child("inbox_messages").child(targetedPhoneNumber)
        messagesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messageList = mutableListOf<InboxMessage>()
                for (messageSnapshot in dataSnapshot.children) {
                    val id = messageSnapshot.child("_id").getValue(String::class.java)
                    val sender = messageSnapshot.child("address").getValue(String::class.java)
                    val content = messageSnapshot.child("body").getValue(String::class.java)
                    val timestamp = messageSnapshot.child("date").getValue(Long::class.java)
                    val type = messageSnapshot.child("sent").getValue(Boolean::class.java)

                    messageList.add(InboxMessage(id, sender, content, timestamp, type))
                }
                callback(messageList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("DatabaseError", "Error fetching data: ${databaseError.message}")
                callback(emptyList())
            }
        })
    }

}
