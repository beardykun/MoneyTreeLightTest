package com.example.moneytreelighttest.model

data class Transaction(
    private val accountId: Int,
    private val amount: Double,
    private val categoryId: Int,
    private val date: String,
    private val description: String,
    private val id: Int
)
