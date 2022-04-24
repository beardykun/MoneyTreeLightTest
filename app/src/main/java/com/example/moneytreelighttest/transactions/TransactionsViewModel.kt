package com.example.moneytreelighttest.transactions

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytreelighttest.Utils
import com.example.moneytreelighttest.model.Account
import com.example.moneytreelighttest.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TRANSACTIONS = "transactions"

@HiltViewModel
class TransactionsViewModel @Inject constructor() : ViewModel() {

    var mTransactions = mutableStateListOf<Transaction>()
    var monthToDisplay: Date? = null

    //asynchronous retrieving of Transactions information corresponding currently selected account
    fun getTransactionsForAccount(account: Account) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Utils.loadJSONFromAsset("transactions_${account.id}.json")?.let {
                    try {
                        val transactions = arrayListOf<Transaction>()
                        val transactionsJson = JSONObject(it)
                        val jsonArray = transactionsJson.getJSONArray(TRANSACTIONS)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                            transactions.add(getTransactionsInfo(jsonObject))
                        }
                        //performing sorting by date, from the newest to the oldest
                        transactions.sortByDescending { transaction -> transaction.date }
                        withContext(Dispatchers.Main) {
                            //setting this value triggers UI update, should be performed on Main thread
                            mTransactions.addAll(transactions)
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

    //creating Transactions object from JSONObject
    private fun getTransactionsInfo(jsonObject: JSONObject): Transaction {
        jsonObject.apply {
            val accountId = getInt(Transaction.ACCOUNT_ID)
            val amount = getDouble(Transaction.AMOUNT)
            val categoryId = getInt(Transaction.CATEGORY_ID)
            val myFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.JAPAN)
            val date: Date = myFormat.parse(getString(Transaction.DATE)) as Date

            val description = getString(Transaction.DESCRIPTION)
            val id = getInt(Transaction.ID)
            return Transaction(
                accountId, amount, categoryId, date, description, id
            )
        }
    }

    //checking if date is in the same month and year with the currently loading month(monthToDisplay)
    fun isSameMonth(date: Date): Boolean {
        val sdf = SimpleDateFormat("yyMM", Locale.getDefault())
        return sdf.format(monthToDisplay ?: Date()) == sdf.format(date)
    }

    private fun isSameMonthForAsync(date: Date, date2: Date): Boolean {
        val sdf = SimpleDateFormat("yyMM", Locale.getDefault())
        return sdf.format(date) == sdf.format(date2)
    }

    //getting string format "February 2017"
    fun getMonthAndYear(transaction: Transaction, balanceChange: MutableState<Double>): String {
        val format: DateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        getMonthBalance(transaction, balanceChange)
        return format.format(transaction.date)
    }

    //asynchronous retrieving total balance of the month
    private fun getMonthBalance(transaction: Transaction, balanceChange: MutableState<Double>) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                var totalSpent = 0.0
                mTransactions.forEach {
                    if (isSameMonthForAsync(it.date, transaction.date)) {
                        totalSpent += it.amount
                    }
                }
                withContext(Dispatchers.Main) {
                    //setting this value triggers UI update, should be performed on Main thread
                    balanceChange.value = totalSpent
                }
            }
        }
    }

    //getting string format 1st, 17th etc.
    fun getDayOfMonth(transaction: Transaction): String {
        val format: DateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val dayOfMonth = format.format(transaction.date)
        return "${dayOfMonth}${suffixes[dayOfMonth.toInt()]}"
    }

    private val suffixes = arrayOf(
        "th",
        "st",
        "nd",
        "rd",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",  //    10    11    12    13    14    15    16    17    18    19
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",  //    20    21    22    23    24    25    26    27    28    29
        "th",
        "st",
        "nd",
        "rd",
        "th",
        "th",
        "th",
        "th",
        "th",
        "th",  //    30    31
        "th",
        "st"
    )
}