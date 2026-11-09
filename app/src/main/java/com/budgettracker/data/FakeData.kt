package com.budgettracker.data

import com.budgettracker.data.model.Budget
import com.budgettracker.data.model.Category
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.Transaction
import com.budgettracker.data.model.TransactionType

object FakeData {
    val categories = listOf(
        Category(1L, "Alimentación", CategoryType.EXPENSE, 0xFF4CAF50, "food"),
        Category(2L, "Transporte", CategoryType.EXPENSE, 0xFF3F51B5, "car"),
        Category(3L, "Casa", CategoryType.EXPENSE, 0xFFF39C12, "home"),
        Category(4L, "Salud", CategoryType.EXPENSE, 0xFF00BCD4, "health"),
        Category(5L, "Entretenimiento", CategoryType.EXPENSE, 0xFFE91E63, "movie"),
        Category(6L, "Salario", CategoryType.INCOME, 0xFF8BC34A, "money"),
        Category(7L, "Freelance", CategoryType.INCOME, 0xFFFFC107, "briefcase")
    )

    val budgets = listOf(
        Budget(1L, 300.0),
        Budget(2L, 200.0),
        Budget(3L, 250.0),
        Budget(5L, 180.0)
    )

    val transactions = listOf(
        Transaction(1L, "Alimentación", 12.0, TransactionType.EXPENSE, 1L, "2024-07-22"),
        Transaction(2L, "Transporte", 8.0, TransactionType.EXPENSE, 2L, "2024-07-22"),
        Transaction(3L, "Restaurantes", 2500.0, TransactionType.EXPENSE, 5L, "2024-07-22"),
        Transaction(4L, "Salario", 2500.0, TransactionType.INCOME, 6L, "2024-07-15"),
        Transaction(5L, "Alimentación", 20.0, TransactionType.EXPENSE, 1L, "2024-07-25"),
        Transaction(6L, "Freelance", 500.0, TransactionType.INCOME, 7L, "2024-07-27"),
        Transaction(7L, "Casa", 120.0, TransactionType.EXPENSE, 3L, "2024-07-28"),
        Transaction(8L, "Salud", 35.0, TransactionType.EXPENSE, 4L, "2024-07-30")
    )
}
