package com.budgettracker.ui

import com.budgettracker.data.model.Budget
import com.budgettracker.data.model.Category
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.Transaction
import com.budgettracker.data.model.TransactionType

data class BudgetUiState(
    val categories: List<Category> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val budgets: List<Budget> = emptyList(),
) {
    val expensesTotal: Double
        get() = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

    val incomeTotal: Double
        get() = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }

    val balance: Double
        get() = incomeTotal - expensesTotal

    val expenseCategories: List<CategorySummary>
        get() = categories.filter { it.type == CategoryType.EXPENSE }.map { category ->
            val amount = transactions
                .filter { it.categoryId == category.id && it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }
            CategorySummary(category.id, category.name, category.color, amount)
        }
}

data class CategorySummary(
    val categoryId: Long,
    val name: String,
    val color: Long,
    val amount: Double,
)
