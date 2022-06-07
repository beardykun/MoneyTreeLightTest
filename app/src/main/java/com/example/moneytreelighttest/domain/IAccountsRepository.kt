package com.example.moneytreelighttest.domain

import androidx.lifecycle.LiveData

interface IAccountsRepository {

    fun getAccountsList(): LiveData<List<Account>>

    fun getTotalBalance(): LiveData<String>
}