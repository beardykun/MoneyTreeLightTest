package com.example.moneytreelighttest

import com.example.moneytreelighttest.model.Account
import java.io.IOException
import java.io.InputStream

class Utils {

    companion object {
        // returning text format JPY2,333
        fun getFormattedSum(account: Account): String {
            val curBalance =
                if (account.currency == "JPY") "%,d".format(account.currentBalance.toInt()) else account.currentBalance
            return "${account.currency}$curBalance"
        }

        fun getFormattedSum(currentBalance: Double): String {
            val curBalance = "%,d".format(currentBalance.toInt())
            return "JPY$curBalance"
        }

        // returning text format 2,333JPY
        fun getFormattedSumForTransaction(account: Account, sum: Double): String {
            val curBalance =
                if (account.currency == "JPY") "%,d".format(sum.toInt()) else sum
            return "$curBalance${account.currency}"
        }

        // getting json file content as a String
        fun loadJSONFromAsset(jsonName: String): String? {
            return try {
                val `is`: InputStream =
                    MTLApplication.instance.assets.open(jsonName)
                val size: Int = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer)
            } catch (ex: IOException) {
                //TODO handle error proper way
                ex.printStackTrace()
                return null
            }
        }
    }
}