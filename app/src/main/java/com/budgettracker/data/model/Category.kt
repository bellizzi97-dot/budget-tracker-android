package com.budgettracker.data.model

enum class CategoryType {
    EXPENSE,
    INCOME
}

data class Category(
    val id: Long,
    val name: String,
    val type: CategoryType,
    val color: Long,
    val icon: String,
)
