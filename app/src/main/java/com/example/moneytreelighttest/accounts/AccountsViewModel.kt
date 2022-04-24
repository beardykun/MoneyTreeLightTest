package com.example.moneytreelighttest.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneytreelighttest.Utils
import com.example.moneytreelighttest.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

private const val ACCOUNTS = "accounts"

@HiltViewModel
class AccountsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val mAccounts = MutableLiveData<ArrayList<Account>?>()
    val mTotalBalance = MutableLiveData<Double>()

    fun getAccounts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Utils.loadJSONFromAsset("accounts.json")?.let {
                    try {
                        val accounts = arrayListOf<Account>()
                        val accountsJson = JSONObject(it)
                        val jsonArray = accountsJson.getJSONArray(ACCOUNTS)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                            val account = getAccountInfo(jsonObject)
                            accounts.add(account)
                        }
                        val totalBalance = calculateTotalBalance(accounts)
                        //sorting by name
                        accounts.sortBy { account -> account.name }
                        withContext(Dispatchers.Main) {
                            mAccounts.postValue(accounts)
                            mTotalBalance.postValue(totalBalance)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        //TODO would be nice to inform user about the problem and optionally retry
                        //TODO track Exception properly
                    }
                }
            }
        }
    }

    //calculating total balance for all accounts
    private fun calculateTotalBalance(accounts: ArrayList<Account>): Double {
        var sum = 0.0
        accounts.forEach { account ->
            sum = sum.plus(account.currentBalanceInBase)
        }
        return sum
    }

    //creating Account object from JSONObject
    private fun getAccountInfo(jsonObject: JSONObject): Account {
        jsonObject.apply {
            val id = getInt(Account.ID)
            val name = getString(Account.NAME)
            val institution = getString(Account.INSTITUTION)
            val currency = getString(Account.CURRENCY)
            val currentBalance = getDouble(Account.CURRENT_BALANCE)
            val currentBalanceInBase = getDouble(Account.CURRENT_BALANCE_IN_BASE)
            return Account(id, name, institution, currency, currentBalance, currentBalanceInBase)
        }
    }
}