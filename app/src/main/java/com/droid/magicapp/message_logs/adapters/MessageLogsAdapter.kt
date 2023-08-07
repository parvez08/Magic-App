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


internal class MessageLogsAdapter(
    private var selectedMessage: ArrayList<InboxMessage>,
    private var context: Context,
    private var mOnSelectedListenerCallback: OnSMSLogSelectedListenerCallback
) :
    RecyclerView.Adapter<MessageLogsAdapter.SMSLogsItemViewHolder>() {

    inner class SMSLogsItemViewHolder(private val smsLogBinding: RowMessageLayoutBinding) :
        RecyclerView.ViewHolder(smsLogBinding.root) {

        fun bind(smsLog: InboxMessage) {
            smsLogBinding.apply {

                tvMessengerNumber.text = smsLog.address

                tvMessageBody.text = smsLog.body

                val formattedCallDate = CallDateUtils.formatCallDate(smsLog.date ?: 0)
                tvDateTime.text = formattedCallDate

                root.setOnClickListener {
                    mOnSelectedListenerCallback.onSelected(adapterPosition)
                }
            }
        }
    }

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

    override fun getItemCount(): Int {
        return selectedMessage.size
    }

    fun addItemAtLast(smsLogModel: InboxMessage) {
        selectedMessage.add(smsLogModel)
        notifyItemInserted(selectedMessage.size - 1)
    }

    fun clearAndAddData(smsLogsItems: ArrayList<InboxMessage>) {
        this.selectedMessage.clear()
        this.selectedMessage.addAll(smsLogsItems)
        this.notifyDataSetChanged()
    }

    fun clear() {
        this.selectedMessage.clear()
        this.notifyDataSetChanged()
    }

}

interface OnSMSLogSelectedListenerCallback {
    fun onSelected(position: Int)
}