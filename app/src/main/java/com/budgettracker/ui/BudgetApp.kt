package com.budgettracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.TransactionType
import com.budgettracker.data.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BudgetViewModel(private val repository: BudgetRepository) : ViewModel() {
    private val categoriesFlow = repository.categoriesFlow
    private val transactionsFlow = repository.transactionsFlow
    private val budgetsFlow = repository.budgetsFlow

    val uiState: StateFlow<BudgetUiState> = combine(categoriesFlow, transactionsFlow, budgetsFlow) { categories, transactions, budgets ->
        BudgetUiState(categories = categories, transactions = transactions, budgets = budgets)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BudgetUiState()
    )

    fun addTransaction(title: String, amount: Double, type: TransactionType, categoryId: Long) {
        if (title.isBlank() || amount <= 0.0) return
        viewModelScope.launch {
            repository.insertTransaction(title, amount, type, categoryId)
        }
    }

    fun addCategory(name: String, type: CategoryType, color: Long) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertCategory(name, type, color)
        }
    }

    fun addBudget(categoryId: Long, limit: Double) {
        if (limit <= 0.0) return
        viewModelScope.launch {
            repository.insertBudget(categoryId, limit)
        }
    }
}

class BudgetViewModelFactory(private val repository: BudgetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BudgetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BudgetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
