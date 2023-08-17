package com.droid.magicapp.message_logs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.droid.magicapp.call_logs.utils.CallDateUtils
import com.droid.magicapp.databinding.FragmentConversationBinding
import com.droid.magicapp.databinding.RowItemReceivedMessageBinding
import com.droid.magicapp.databinding.RowItemSentMessageBinding
import com.droid.magicapp.message_logs.models.InboxMessage
import com.droid.magicapp.utils.GenericRVRowBindingViewHolder
import com.droid.magicapp.utils.GenericRecyclerViewAdapter
import com.droid.magicapp.utils.MessageTypeEnum
import java.lang.IllegalArgumentException


class ConversationFragment : Fragment() {
    private lateinit var binding: FragmentConversationBinding
    private lateinit var sMSLogsAdapter: GenericRecyclerViewAdapter<InboxMessage>
    private var convoList: ArrayList<InboxMessage>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(KEY_CONVO_LIST)) {
                convoList = it.getParcelableArrayList(KEY_CONVO_LIST)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentConversationBinding.inflate(layoutInflater).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        setUpRV()
        sMSLogsAdapter.clearAndAddData(convoList)
    }

    private fun setUpRV() {
        sMSLogsAdapter = object : GenericRecyclerViewAdapter<InboxMessage>(requireContext()) {

            override fun onBindViewHolder(
                holder: GenericRVRowBindingViewHolder<*>,
                position: Int
            ) {
                val smsLog = getItem(position)

                when (smsLog?.isSent) {
                    true -> {
                        val sentSMSBinding = holder.binding as RowItemSentMessageBinding
                        configureMessageView(sentSMSBinding, smsLog)
                    }

                    false -> {
                        val receivedSMSBinding = holder.binding as RowItemReceivedMessageBinding
                        configureMessageView(receivedSMSBinding, smsLog)
                    }

                    else -> throw IllegalArgumentException("Invalid Boolean type")
                }
            }

            private fun <T : ViewBinding> configureMessageView(binding: T, smsLog: InboxMessage?) {
                when (binding) {
                    is RowItemSentMessageBinding -> {
                        binding.tvMessengerNumber.text = smsLog?.address
                        binding.tvMessageBody.text = smsLog?.body

                        val formattedCallDate = CallDateUtils.formatCallDate(smsLog?.date ?: 0)
                        binding.tvDateTime.text = "$formattedCallDate  ✔✔"
                    }

                    is RowItemReceivedMessageBinding -> {
                        binding.tvMessengerNumber.text = smsLog?.address
                        binding.tvMessageBody.text = smsLog?.body

                        val formattedCallDate = CallDateUtils.formatCallDate(smsLog?.date ?: 0)
                        binding.tvDateTime.text = formattedCallDate
                    }

                    else -> throw IllegalArgumentException("Invalid ViewBinding type")
                }
            }


            override fun getViewHolder(
                context: Context?,
                parentView: ViewGroup?,
                itemViewType: Int
            ): GenericRVRowBindingViewHolder<*> = when (itemViewType) {
                MessageTypeEnum.SMS_RECEIVED.type -> {
                    RowItemReceivedMessageBinding.inflate(layoutInflater).let {
                        GenericRVRowBindingViewHolder(it.root, it)
                    }
                }

                MessageTypeEnum.SMS_SENT.type -> {
                    RowItemSentMessageBinding.inflate(layoutInflater).let {
                        GenericRVRowBindingViewHolder(it.root, it)
                    }
                }

                else -> {
                    throw IllegalArgumentException("Invalid View Type")
                }
            }

            override fun getItemViewType(position: Int): Int {
                val sMSResponseModel = getItem(position)
                return when (sMSResponseModel?.isSent) {
                    true -> {
                        MessageTypeEnum.SMS_SENT.type
                    }

                    false -> {
                        MessageTypeEnum.SMS_RECEIVED.type
                    }

                    else -> throw IllegalArgumentException("Invalid Boolean value")
                }
            }


        }

        binding.rvSMSLogs.apply {
            adapter = sMSLogsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        }
    }

    private fun setSentMsgData() {

    }

    companion object {
        val TAG2: String? = ConversationFragment::class.java.canonicalName
        private const val KEY_CONVO_LIST = "KEY_CONVO_LIST"

        @JvmStatic
        fun newInstance(value: List<InboxMessage>) =
            ConversationFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_CONVO_LIST, ArrayList(value))
                }
            }
    }
}