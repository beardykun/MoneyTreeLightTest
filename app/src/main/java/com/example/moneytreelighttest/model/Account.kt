package com.example.moneytreelighttest.model

data class Account(
    private val id: Int,
    private val name: String,
    private val institution: String,
    private val currency: String,
    private val currentBalance: Double,
    private val currentBalanceInBase: Double
) {

}