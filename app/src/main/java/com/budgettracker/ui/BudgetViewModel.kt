package com.budgettracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BudgetApp() {
    val navController = rememberNavController()
    val viewModel: BudgetViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color(0xFF0E7C67),
        bottomBar = {
            BudgetBottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    state = uiState,
                    onOpenTransactions = { navController.navigate("transactions") },
                    onOpenBudgets = { navController.navigate("budgets") }
                )
            }
            composable("transactions") {
                TransactionsScreen(
                    state = uiState,
                    onAddTransaction = { title, amount, type, categoryId ->
                        viewModel.addTransaction(title, amount, type, categoryId)
                    }
                )
            }
            composable("categories") {
                CategoriesScreen(
                    categories = uiState.categories,
                    onAddCategory = { name, type, color ->
                        viewModel.addCategory(name, type, color)
                    }
                )
            }
            composable("budgets") {
                BudgetsScreen(
                    state = uiState,
                    onAddBudget = { categoryId, limit ->
                        viewModel.addBudget(categoryId, limit)
                    }
                )
            }
        }
    }
}

@Composable
private fun BudgetBottomBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color(0xFF0A5D50),
    ) {
        val items = listOf(
            "home" to "Inicio",
            "transactions" to "Operaciones",
            "categories" to "Categorías",
            "budgets" to "Presupuestos"
        )

        items.forEach { (route, label) ->
            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == route,
                onClick = { navController.navigate(route) },
                label = { Text(text = label) },
                icon = { }
            )
        }
    }
}

@Composable
fun HomeScreen(
    state: BudgetUiState,
    onOpenTransactions: () -> Unit,
    onOpenBudgets: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E7C67))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "CONTROL EXHAUSTIVO\nDE GASTOS E INGRESOS",
            color = Color.White,
            modifier = Modifier.padding(top = 12.dp)
        )

        BudgetSummaryCard(state = state, onOpenTransactions = onOpenTransactions, onOpenBudgets = onOpenBudgets)
    }
}

@Composable
private fun BudgetSummaryCard(
    state: BudgetUiState,
    onOpenTransactions: () -> Unit,
    onOpenBudgets: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE9EAE6),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("BALANCE", color = Color(0xFF6B7A7A), fontSize = androidx.compose.ui.unit.TextUnit.Unspecified)
            Text("${state.balance} $", color = TextPrimary)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("GASTOS", color = Color(0xFF6B7A7A))
            Text("INGRESOS", color = Color(0xFF6B7A7A))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${state.expensesTotal} $", color = Color.Red)
            Text("${state.incomeTotal} $", color = Color(0xFF1B9D5B))
        }

        CategoryChart(state)

        state.expenseCategories
            .sortedByDescending { it.amount }
            .take(4)
            .forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(item.name)
                    Text("${item.amount} $")
                }
            }
    }
}

@Composable
private fun CategoryChart(state: BudgetUiState) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = androidx.compose.ui.Modifier.size(180.dp)) {
            val total = state.expensesTotal.coerceAtLeast(1.0)
            var startAngle = -90f
            val slices = state.expenseCategories
            for (slice in slices) {
                val sweep = (slice.amount / total).toFloat() * 360f
                drawArc(
                    color = Color(slice.color),
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true
                )
                startAngle += sweep
            }
        }
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Modifier.size(90.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(text = "${state.balance} $", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        }
    }
}
