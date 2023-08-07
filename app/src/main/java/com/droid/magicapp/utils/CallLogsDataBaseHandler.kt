package com.droid.magicapp.utils


import android.util.Log
import com.droid.magicapp.call_logs.models.CallLogEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.koin.java.KoinJavaComponent.inject

class CallLogsDataBaseHandler {

    private val database: DatabaseReference by inject(DatabaseReference::class.java)

    fun fetchCallLogsForNumber(
        targetedPhoneNumber: String,
        callback: (List<CallLogEntry>) -> Unit
    ) {
        val callLogsReference = database.child("call_logs").child(targetedPhoneNumber)
        callLogsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val callLogList = mutableListOf<CallLogEntry>()
                for (callLogSnapshot in dataSnapshot.children) {
                    val callDate = callLogSnapshot.child("callDate").getValue(Long::class.java)
                    val callDuration =
                        callLogSnapshot.child("callDuration").getValue(Long::class.java)
                    val callType = callLogSnapshot.child("callType").getValue(Int::class.java)
                    val phoneNumber =
                        callLogSnapshot.child("phoneNumber").getValue(String::class.java)

                    callLogList.add(CallLogEntry(phoneNumber, callType, callDate, callDuration))
                }
                callback(callLogList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("DatabaseError", "Error fetching data: ${databaseError.message}")
                callback(emptyList())
            }
        })
    }
}