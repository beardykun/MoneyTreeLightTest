package com.example.moneytreelighttest.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytreelighttest.R
import com.example.moneytreelighttest.Utils
import com.example.moneytreelighttest.model.Account

class AccountsAdapter(private val accounts: ArrayList<Account>) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {

    interface OnAccountClickListener {
        fun onAccountClick(account: Account)
    }

    private var mListener: OnAccountClickListener? = null

    fun setListener(listener: OnAccountClickListener) {
        mListener = listener
    }

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
            holder.tvCardBalance.text = Utils.getFormattedSum(this)

            holder.itemView.setOnClickListener {
                mListener?.onAccountClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}