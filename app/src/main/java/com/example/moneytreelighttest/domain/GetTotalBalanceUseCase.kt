package com.example.moneytreelighttest.domain

import androidx.lifecycle.LiveData

class GetTotalBalanceUseCase(private val repository: IAccountsRepository) {

    fun getTotalBalance(): LiveData<String> {
        return repository.getTotalBalance()
    }
}