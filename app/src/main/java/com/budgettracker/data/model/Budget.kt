package com.budgettracker.data.model

data class Budget(
    val id: Long = 0L,
    val categoryId: Long,
    val limit: Double
)
