package com.example.moneytreelighttest.model


data class Account(
    val id: Int,
    val name: String,
    val institution: String,
    val currency: String,
    val currentBalance: Double,
    val currentBalanceInBase: Double
) {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val INSTITUTION = "institution"
        const val CURRENCY = "currency"
        const val CURRENT_BALANCE = "current_balance"
        const val CURRENT_BALANCE_IN_BASE = "current_balance_in_base"
    }
}