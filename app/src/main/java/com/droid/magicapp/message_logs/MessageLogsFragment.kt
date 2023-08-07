package com.droid.magicapp.message_logs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.droid.magicapp.databinding.FragmentMessageLogsBinding
import com.droid.magicapp.message_logs.adapters.MessageLogsAdapter
import com.droid.magicapp.message_logs.adapters.OnSMSLogSelectedListenerCallback
import com.droid.magicapp.message_logs.models.InboxMessage
import com.droid.magicapp.message_logs.utils.MessageLogsDataBaseHandler


class MessageLogsFragment : Fragment() {
    private lateinit var binding: FragmentMessageLogsBinding
    private val messageLogsDataBaseHandler: MessageLogsDataBaseHandler =
        MessageLogsDataBaseHandler()
    private val phoneNumber: String = "7760237886"
    private var messageLogsList: ArrayList<InboxMessage> = arrayListOf()
    private lateinit var messageLogsAdapter: MessageLogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMessageLogsBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageLogsDataBaseHandler.fetchMessagesForNumber(phoneNumber) { messageList ->
            messageLogsList = messageList as ArrayList<InboxMessage>
            messageLogsAdapter.clearAndAddData(messageLogsList)
        }
        setUpRv()
    }

    private fun setUpRv() {
        messageLogsAdapter = MessageLogsAdapter(
            messageLogsList,
            requireContext(),
            object : OnSMSLogSelectedListenerCallback {
                override fun onSelected(position: Int) {

                }

            }
        )
        binding.rvSMSLogs.apply {
            adapter = messageLogsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        val TAG2: String? = MessageLogsFragment::class.java.canonicalName

        @JvmStatic
        fun newInstance() =
            MessageLogsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}