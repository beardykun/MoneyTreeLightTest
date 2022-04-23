package com.example.moneytreelighttest.model

import java.util.*

data class Transaction(
    private val accountId: Int,
    private val amount: Double,
    private val categoryId: Int,
    private val date: Date,
    private val description: String,
    private val id: Int
)
