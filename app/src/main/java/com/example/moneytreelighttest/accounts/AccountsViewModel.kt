package com.example.moneytreelighttest.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneytreelighttest.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
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
                loadJSONFromAsset("accounts.json")?.let {
                    try {
                        val accounts = arrayListOf<Account>()
                        val accountsJson = JSONObject(it)
                        val jsonArray = accountsJson.getJSONArray(ACCOUNTS)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                            accounts.add(getAccountInfo(jsonObject))
                        }

                        val totalBalance = calculateTotalBalance(accounts)
                        withContext(Dispatchers.Main) {
                            mAccounts.postValue(accounts)
                            mTotalBalance.postValue(totalBalance)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        mAccounts.postValue(null)
                    }
                }
            }
        }
    }

    private fun calculateTotalBalance(accounts: ArrayList<Account>): Double {
        var sum = 0.0
        accounts.forEach { account ->
            sum = sum.plus(account.currentBalanceInBase)
        }
        return sum
    }


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

    private fun loadJSONFromAsset(jsonName: String): String? {
        return try {
            val `is`: InputStream =
                getApplication<Application>().assets.open(jsonName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}