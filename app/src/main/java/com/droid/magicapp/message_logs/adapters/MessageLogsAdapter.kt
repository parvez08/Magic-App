package com.droid.magicapp.message_logs.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.droid.magicapp.call_logs.utils.CallDateUtils
import com.droid.magicapp.databinding.RowMessageLayoutBinding
import com.droid.magicapp.message_logs.models.InboxMessage

class MessageLogsAdapter(
    private val context: Context,
    private val mOnSelectedListenerCallback: OnSMSLogSelectedListenerCallback
) :
    RecyclerView.Adapter<MessageLogsAdapter.SMSLogsItemViewHolder>() {

    private var allMessagesList: Map<String, List<InboxMessage>> = emptyMap()
    private val selectedMessage = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSLogsItemViewHolder {
        val smsLogsBinding = RowMessageLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SMSLogsItemViewHolder(smsLogsBinding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: SMSLogsItemViewHolder, position: Int) {
        val smsLogsItemModel = selectedMessage[position]
        holder.bind(smsLogsItemModel)
    }

    override fun getItemCount(): Int = selectedMessage.size

    fun clearAndAddData(smsLogsItems: List<String>, allMessages: Map<String, List<InboxMessage>>) {
        allMessagesList = allMessages
        selectedMessage.clear()
        selectedMessage.addAll(smsLogsItems)
        notifyDataSetChanged()
    }

    fun clear() {
        selectedMessage.clear()
        notifyDataSetChanged()
    }

    inner class SMSLogsItemViewHolder(private val smsLogBinding: RowMessageLayoutBinding) :
        RecyclerView.ViewHolder(smsLogBinding.root) {

        fun bind(smsLog: String) {
            smsLogBinding.apply {
                val messagesForNumber = allMessagesList[smsLog]
                val firstMessage = messagesForNumber?.getOrNull(0)

                tvMessengerNumber.text = smsLog
                tvMessageBody.text = firstMessage?.body

                val formattedCallDate = CallDateUtils.formatCallDate(firstMessage?.date ?: 0)
                tvDateTime.text = formattedCallDate

                root.setOnClickListener {
                    messagesForNumber?.let {
                        mOnSelectedListenerCallback.onSelected(adapterPosition, it)
                    }
                }
            }
        }
    }
}

interface OnSMSLogSelectedListenerCallback {
    fun onSelected(position: Int, value: List<InboxMessage>)
}
