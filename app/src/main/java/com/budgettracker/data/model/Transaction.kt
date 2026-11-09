package com.budgettracker.data.model

enum class TransactionType {
    EXPENSE,
    INCOME
}

data class Transaction(
    val id: Long = 0L,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Long,
    val date: Long
)
