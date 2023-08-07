package com.droid.magicapp.call_logs.adapters


import android.content.Context
import android.os.Build
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.droid.magicapp.R
import com.droid.magicapp.call_logs.models.CallLogEntry
import com.droid.magicapp.call_logs.utils.CallDateUtils
import com.droid.magicapp.call_logs.utils.CallDurationUtils
import com.droid.magicapp.databinding.RowCallLogLayoutBinding


internal class CallLogsAdapter(
    private var selectedProduct: ArrayList<CallLogEntry>,
    private var context: Context,
    private var mOnSelectedListenerCallback: OnCallLogSelectedListenerCallback
) :
    RecyclerView.Adapter<CallLogsAdapter.CallLogsItemViewHolder>() {

    inner class CallLogsItemViewHolder(private val callLogBinding: RowCallLogLayoutBinding) :
        RecyclerView.ViewHolder(callLogBinding.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(callLog: CallLogEntry) {
            callLogBinding.apply {

                tvNumber.text = callLog.phoneNumber

                val callTypeMapping = mapOf(
                    CallLog.Calls.INCOMING_TYPE to R.string.incoming_call_at,
                    CallLog.Calls.OUTGOING_TYPE to R.string.outgoing_call_at,
                    CallLog.Calls.MISSED_TYPE to R.string.missed_call_at,
                    CallLog.Calls.REJECTED_TYPE to R.string.rejected_call_at,
                    CallLog.Calls.BLOCKED_TYPE to R.string.blocked_number_call_at,
                    CallLog.Calls.VOICEMAIL_TYPE to R.string.voicemail_call_at
                )

                val callTypeResourceId =
                    callTypeMapping[callLog.callType] ?: R.string.unknown_call_at
                tvCallType.text = context.getString(callTypeResourceId)

                val callDurationInSeconds = callLog.callDuration ?: 0
                val formattedDuration = CallDurationUtils.formatCallDuration(callDurationInSeconds)
                tvDuration.text = formattedDuration

                val formattedCallDate = CallDateUtils.formatCallDate(callLog.callDate ?: 0)
                tvDateTime.text = formattedCallDate

                root.setOnClickListener {
                    mOnSelectedListenerCallback.onSelected(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogsItemViewHolder {
        val callLogsBinding = RowCallLogLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CallLogsItemViewHolder(callLogsBinding)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CallLogsItemViewHolder, position: Int) {
        val callLogsItemModel = selectedProduct[position]
        holder.bind(callLogsItemModel)
    }

    override fun getItemCount(): Int {
        return selectedProduct.size
    }

    fun addItemAtLast(callLogModel: CallLogEntry) {
        selectedProduct.add(callLogModel)
        notifyItemInserted(selectedProduct.size - 1)
    }

    fun clearAndAddData(callLogsItems: ArrayList<CallLogEntry>) {
        this.selectedProduct.clear()
        this.selectedProduct.addAll(callLogsItems)
        this.notifyDataSetChanged()
    }

    fun clear() {
        this.selectedProduct.clear()
        this.notifyDataSetChanged()
    }

}

interface OnCallLogSelectedListenerCallback {
    fun onSelected(position: Int)
}