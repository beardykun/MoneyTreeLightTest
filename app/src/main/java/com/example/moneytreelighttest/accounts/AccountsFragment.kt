package com.example.moneytreelighttest.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moneytreelighttest.BaseFragment
import com.example.moneytreelighttest.databinding.FragmentAccountsBinding

class AccountsFragment : BaseFragment() {

    private lateinit var binding: FragmentAccountsBinding
    private val viewModel: AccountsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //we can display a progress bap while waiting for the loading of accounts information
        //showProgressView()

        //request data from Json
        viewModel.getAccounts()
        //start observing mAccounts list for any data updates
        viewModel.mAccounts.observe(viewLifecycleOwner) { accounts ->
            //setting an adapter to recycler view upon receiving data update
            accounts?.let { binding.rvAccounts.adapter = AccountsAdapter(it) }
            //hiding progress bar if was displaying it earlier
            //hideProgressView()
        }

        viewModel.mTotalBalance.observe(viewLifecycleOwner) {
            val totalBalanceText = "JPY${it.toInt()}"
            binding.tvTotalBalance.text = totalBalanceText
        }
    }
}