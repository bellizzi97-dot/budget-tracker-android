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

    val transactions = listOf(
        Transaction(1L, "Alimentación", 12.0, TransactionType.EXPENSE, 1L, System.currentTimeMillis() - 86400000L),
        Transaction(2L, "Transporte", 8.0, TransactionType.EXPENSE, 2L, System.currentTimeMillis() - 172800000L),
        Transaction(3L, "Restaurant", 2500.0, TransactionType.EXPENSE, 5L, System.currentTimeMillis() - 345600000L),
        Transaction(4L, "Salario", 2500.0, TransactionType.INCOME, 6L, System.currentTimeMillis() - 1209600000L),
        Transaction(5L, "Mercado", 20.0, TransactionType.EXPENSE, 1L, System.currentTimeMillis() - 604800000L),
        Transaction(6L, "Freelance", 500.0, TransactionType.INCOME, 7L, System.currentTimeMillis() - 2592000000L),
        Transaction(7L, "Casa", 120.0, TransactionType.EXPENSE, 3L, System.currentTimeMillis() - 691200000L),
        Transaction(8L, "Salud", 35.0, TransactionType.EXPENSE, 4L, System.currentTimeMillis() - 345600000L)
    )

    val budgets = listOf(
        Budget(1L, 1L, 300.0),
        Budget(2L, 2L, 200.0),
        Budget(3L, 3L, 250.0),
        Budget(4L, 5L, 180.0)
    )
}
