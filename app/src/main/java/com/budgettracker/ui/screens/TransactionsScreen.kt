package com.budgettracker.ui

import androidx.lifecycle.ViewModel
import com.budgettracker.data.FakeData
import com.budgettracker.data.model.Budget
import com.budgettracker.data.model.Category
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.Transaction
import com.budgettracker.data.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class BudgetUiState(
    val categories: List<Category> = FakeData.categories,
    val transactions: List<Transaction> = FakeData.transactions,
    val budgets: List<Budget> = FakeData.budgets,
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

class BudgetViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    fun addTransaction(title: String, amount: Double, type: TransactionType, categoryId: Long) {
        val nextId = (_uiState.value.transactions.maxOfOrNull { it.id } ?: 0L) + 1L
        val transaction = Transaction(
            id = nextId,
            title = title,
            amount = amount,
            type = type,
            categoryId = categoryId,
            date = "Hoy"
        )
        _uiState.value = _uiState.value.copy(
            transactions = listOf(transaction) + _uiState.value.transactions
        )
    }

    fun addCategory(name: String, type: CategoryType, color: Long) {
        if (name.isBlank()) return
        val nextId = (_uiState.value.categories.maxOfOrNull { it.id } ?: 0L) + 1L
        val category = Category(
            id = nextId,
            name = name,
            type = type,
            color = color,
            icon = "custom"
        )
        _uiState.value = _uiState.value.copy(categories = _uiState.value.categories + category)
    }

    fun addBudget(categoryId: Long, limit: Double) {
        if (limit <= 0.0) return
        val existing = _uiState.value.budgets.firstOrNull { it.categoryId == categoryId }
        val updated = if (existing == null) {
            _uiState.value.budgets + Budget(categoryId, limit)
        } else {
            _uiState.value.budgets.map {
                if (it.categoryId == categoryId) it.copy(limit = limit) else it
            }
        }
        _uiState.value = _uiState.value.copy(budgets = updated)
    }
}
