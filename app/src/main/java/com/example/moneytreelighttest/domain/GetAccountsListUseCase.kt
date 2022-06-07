package com.example.moneytreelighttest.domain

import androidx.lifecycle.LiveData

class GetAccountsListUseCase(private val repository: IAccountsRepository) {

    fun getAccountsList(): LiveData<List<Account>> {
        return repository.getAccountsList()
    }
}