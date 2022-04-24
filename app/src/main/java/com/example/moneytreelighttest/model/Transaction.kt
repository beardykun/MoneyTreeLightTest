package com.example.moneytreelighttest.model

import java.util.*


data class Transaction(
    val accountId: Int,
    val amount: Double,
    val categoryId: Int,
    val date: Date,
    val description: String,
    val id: Int
) {
    companion object {
        const val ACCOUNT_ID = "account_id"
        const val AMOUNT = "amount"
        const val CATEGORY_ID = "category_id"
        const val DATE = "date"
        const val DESCRIPTION = "description"
        const val ID = "id"
    }
}
