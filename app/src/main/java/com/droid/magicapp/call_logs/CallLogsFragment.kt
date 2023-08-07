package com.droid.magicapp.call_logs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.droid.magicapp.call_logs.adapters.CallLogsAdapter
import com.droid.magicapp.call_logs.adapters.OnCallLogSelectedListenerCallback
import com.droid.magicapp.call_logs.models.CallLogEntry
import com.droid.magicapp.databinding.FragmentCallLogsBinding
import com.droid.magicapp.call_logs.utils.CallLogsDataBaseHandler


class CallLogsFragment : Fragment() {
    private lateinit var binding: FragmentCallLogsBinding
    private val callLogsDataBaseHandler: CallLogsDataBaseHandler = CallLogsDataBaseHandler()
    private val phoneNumber: String = ""
    private var callLogsList: ArrayList<CallLogEntry> = arrayListOf()
    private lateinit var callLogsAdapter: CallLogsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCallLogsBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callLogsDataBaseHandler.fetchCallLogsForNumber(phoneNumber) { callLogList ->
            callLogsList = callLogList as ArrayList<CallLogEntry>
            callLogsAdapter.clearAndAddData(callLogsList)
        }
        setUpRv()
    }

    private fun setUpRv() {
        callLogsAdapter = CallLogsAdapter(
            callLogsList,
            requireContext(),
            object : OnCallLogSelectedListenerCallback {
                override fun onSelected(position: Int) {

                }

            }
        )
        binding.rvCallLogs.apply {
            adapter = callLogsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        val TAG2: String? = CallLogsFragment::class.java.canonicalName

        @JvmStatic
        fun newInstance() =
            CallLogsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}