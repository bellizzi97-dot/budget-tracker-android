package com.budgettracker.data.model

enum class TransactionType {
    EXPENSE,
    INCOME
}

data class Transaction(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Long,
    val date: String,
)
