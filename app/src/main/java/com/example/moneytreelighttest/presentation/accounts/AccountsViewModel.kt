package com.example.moneytreelighttest.presentation.accounts

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import com.example.moneytreelighttest.data.AccountsRepositoryImpl
import com.example.moneytreelighttest.domain.GetAccountsListUseCase
import com.example.moneytreelighttest.domain.GetTotalBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    application: Application,
    repository: AccountsRepositoryImpl
) : AndroidViewModel(application) {

    private val getAccountsList = GetAccountsListUseCase(repository)
    private val getTotalBalance = GetTotalBalanceUseCase(repository)

    val mAccounts = getAccountsList.getAccountsList()
    val mTotalBalance = getTotalBalance.getTotalBalance()

    private var mListPosition: Parcelable? = null
    var listPosition: Parcelable?
        get() = mListPosition
        set(value) {
            mListPosition = value
        }
}