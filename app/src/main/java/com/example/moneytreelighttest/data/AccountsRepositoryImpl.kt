package com.example.moneytreelighttest.data

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moneytreelighttest.Utils
import com.example.moneytreelighttest.domain.Account
import com.example.moneytreelighttest.domain.IAccountsRepository
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject

private const val ACCOUNTS = "accounts"

object AccountsRepositoryImpl : IAccountsRepository {

    private val accountsList = mutableListOf<Account>()
    private val accountsListLD = MutableLiveData<List<Account>>()
    private val totalSumLD = MutableLiveData<String>()
    private val defaultScope = CoroutineScope(Dispatchers.Default + Job())

    init {
        defaultScope.launch(Dispatchers.IO) {
            initAccountsList()
        }
    }

    private suspend fun initAccountsList() {
        coroutineScope {

        }
        Utils.loadJSONFromAsset("accounts.json")?.let {
            try {
                val accountsJson = JSONObject(it)
                val accountsJSONArray = accountsJson.getJSONArray(ACCOUNTS)
                getAccountsListFormJSONArray(accountsJSONArray)
            } catch (e: Exception) {
                e.printStackTrace()
                //TODO would be nice to inform user about the problem and optionally retry
                //TODO track Exception properly
            }
        }
    }

    private fun getAccountsListFormJSONArray(accountsJSONArray: JSONArray) {
        for (i in 0 until accountsJSONArray.length()) {
            val jsonObject: JSONObject = accountsJSONArray.getJSONObject(i)
            val account = getAccount(jsonObject)
            accountsList.add(account)
        }
        //sorting by name
        accountsList.sortBy { account -> account.name }
        Log.i("TAGGER", (Thread.currentThread() == Looper.getMainLooper().thread).toString())
        calculateTotalBalance()
        accountsListLD.postValue(accountsList.toList())
    }


    override fun getAccountsList(): LiveData<List<Account>> {
        return accountsListLD
    }

    private fun getAccount(jsonObject: JSONObject): Account {
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

    private fun calculateTotalBalance() {
        var sum = 0.0
        accountsList.forEach { account ->
            sum = sum.plus(account.currentBalanceInBase)
        }
        val formattedSum = Utils.getFormattedSum(sum)
        totalSumLD.postValue(formattedSum)
    }

    override fun getTotalBalance(): LiveData<String> {
        return totalSumLD
    }
}