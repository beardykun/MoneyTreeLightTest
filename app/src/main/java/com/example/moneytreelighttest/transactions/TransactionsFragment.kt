package com.example.moneytreelighttest.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.navigation.fragment.navArgs
import com.example.moneytreelighttest.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment: BaseFragment() {

    private val args: TransactionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView {
            args.account.currency?.let { Text(it) }
        }
    }
}