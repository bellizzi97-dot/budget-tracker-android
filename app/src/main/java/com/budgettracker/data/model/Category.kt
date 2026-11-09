package com.budgettracker.data.model

enum class CategoryType {
    EXPENSE,
    INCOME
}

data class Category(
    val id: Long = 0L,
    val name: String,
    val type: CategoryType,
    val color: Long = 0xFF4CAF50,
    val icon: String = "default"
)
