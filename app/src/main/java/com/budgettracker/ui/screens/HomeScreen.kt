package com.budgettracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.budgettracker.data.repository.BudgetRepository
import com.budgettracker.ui.screens.BudgetsScreen
import com.budgettracker.ui.screens.CategoriesScreen
import com.budgettracker.ui.screens.HomeScreen
import com.budgettracker.ui.screens.TransactionsScreen

@Composable
fun BudgetApp(repository: BudgetRepository) {
    val navController = rememberNavController()
    val viewModel: BudgetViewModel = viewModel(factory = BudgetViewModelFactory(repository))
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
            composable("home") { HomeScreen(uiState = uiState, navController = navController) }
            composable("transactions") {
                TransactionsScreen(
                    uiState = uiState,
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
                    uiState = uiState,
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"

    NavigationBar(containerColor = Color(0xFF0A5D50)) {
        val items = listOf(
            "home" to "Inicio",
            "transactions" to "Operaciones",
            "categories" to "Categorías",
            "budgets" to "Presupuestos"
        )

        items.forEach { (route, label) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { navController.navigate(route) },
                label = { Text(label, color = Color.White) },
                icon = { }
            )
        }
    }
}
