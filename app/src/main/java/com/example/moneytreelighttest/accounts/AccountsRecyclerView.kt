package com.example.moneytreelighttest.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytreelighttest.R
import com.example.moneytreelighttest.model.Account

class AccountsRecyclerView(private val accounts: ArrayList<Account>) :
    RecyclerView.Adapter<AccountsRecyclerView.AccountsViewHolder>() {

    class AccountsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInstitutionName: TextView = view.findViewById(R.id.tvInstitutionName)
        val tvCardName: TextView = view.findViewById(R.id.tvCardName)
        val tvCardBalance: TextView = view.findViewById(R.id.tvCardBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.accounts_list_item, parent, false)
        return AccountsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        accounts[position].apply {
            holder.tvInstitutionName.text = institution
            holder.tvCardName.text = name
            val balance = "$currency$currentBalance"
            holder.tvCardBalance.text = balance
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}