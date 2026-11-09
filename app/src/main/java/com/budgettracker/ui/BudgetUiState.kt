package com.budgettracker.data.repository

import com.budgettracker.data.FakeData
import com.budgettracker.data.local.AppDatabase
import com.budgettracker.data.local.BudgetEntity
import com.budgettracker.data.local.CategoryEntity
import com.budgettracker.data.local.TransactionEntity
import com.budgettracker.data.model.Budget
import com.budgettracker.data.model.Category
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.Transaction
import com.budgettracker.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class BudgetRepository(private val db: AppDatabase) {
    init {
        runBlocking {
            val categories = db.categoryDao().getAll().first()
            if (categories.isEmpty()) {
                db.categoryDao().insertAll(*FakeData.categories.map { it.toEntity() }.toTypedArray())
            }
            val transactions = db.transactionDao().getAll().first()
            if (transactions.isEmpty()) {
                FakeData.transactions.forEach { db.transactionDao().insert(it.toEntity()) }
            }
            val budgets = db.budgetDao().getAll().first()
            if (budgets.isEmpty()) {
                FakeData.budgets.forEach { db.budgetDao().insert(it.toEntity()) }
            }
        }
    }

    val categoriesFlow: Flow<List<Category>> = db.categoryDao().getAll().map { list ->
        list.map { it.toModel() }
    }

    val transactionsFlow: Flow<List<Transaction>> = db.transactionDao().getAll().map { list ->
        list.map { it.toModel() }
    }

    val budgetsFlow: Flow<List<Budget>> = db.budgetDao().getAll().map { list ->
        list.map { it.toModel() }
    }

    suspend fun insertTransaction(title: String, amount: Double, type: TransactionType, categoryId: Long) {
        val entity = TransactionEntity(
            id = System.currentTimeMillis(),
            title = title,
            amount = amount,
            type = type.name,
            categoryId = categoryId,
            date = System.currentTimeMillis()
        )
        db.transactionDao().insert(entity)
    }

    suspend fun insertCategory(name: String, type: CategoryType, color: Long) {
        val entity = CategoryEntity(
            id = System.currentTimeMillis(),
            name = name,
            type = type.name,
            color = color,
            icon = "custom"
        )
        db.categoryDao().insertAll(entity)
    }

    suspend fun insertBudget(categoryId: Long, limit: Double) {
        val existing = db.budgetDao().getAll().first().firstOrNull { it.categoryId == categoryId }
        val entity = BudgetEntity(
            id = existing?.id ?: System.currentTimeMillis(),
            categoryId = categoryId,
            limit = limit
        )
        db.budgetDao().insert(entity)
    }
}

private fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    type = type.name,
    color = color,
    icon = icon
)

private fun CategoryEntity.toModel() = Category(
    id = id,
    name = name,
    type = CategoryType.valueOf(type),
    color = color,
    icon = icon
)

private fun Transaction.toEntity() = TransactionEntity(
    id = id,
    title = title,
    amount = amount,
    type = type.name,
    categoryId = categoryId,
    date = date
)

private fun TransactionEntity.toModel() = Transaction(
    id = id,
    title = title,
    amount = amount,
    type = TransactionType.valueOf(type),
    categoryId = categoryId,
    date = date
)

private fun Budget.toEntity() = BudgetEntity(
    id = id,
    categoryId = categoryId,
    limit = limit
)

private fun BudgetEntity.toModel() = Budget(
    id = id,
    categoryId = categoryId,
    limit = limit
)
