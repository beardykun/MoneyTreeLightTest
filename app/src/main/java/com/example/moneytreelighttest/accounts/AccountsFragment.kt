package com.example.moneytreelighttest.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moneytreelighttest.BaseFragment
import com.example.moneytreelighttest.databinding.FragmentAccountsBinding
import com.example.moneytreelighttest.model.Account
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsFragment : BaseFragment(), AccountsAdapter.OnAccountClickListener {

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
            accounts?.let {
                val adapter = AccountsAdapter(it)
                adapter.setListener(this)
                binding.rvAccounts.adapter = adapter
            }
            //hiding progress bar if was displaying it earlier
            //hideProgressView()
        }

        // observe mTotalBalance for any updates
        viewModel.mTotalBalance.observe(viewLifecycleOwner) {
            val totalBalanceText = "JPY${it.toInt()}"
            binding.tvTotalBalance.text = totalBalanceText
        }
    }

    override fun onAccountClick(account: Account) {
        // navigating to Transactions screen
        showProgressView()
        findNavController().navigate(AccountsFragmentDirections.actionAccountsFragmentToTransactionsFragment(account))
    }
}