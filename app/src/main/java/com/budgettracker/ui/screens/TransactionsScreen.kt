package com.budgettracker.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.budgettracker.ui.BudgetUiState

@Composable
fun HomeScreen(uiState: BudgetUiState, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E7C67))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CONTROL EXHAUSTIVO\nDE GASTOS E INGRESOS",
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE9EAE6))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("BALANCE", color = Color(0xFF6B7A7A))
                    Text("${String.format("%.0f", uiState.balance)} $", color = Color(0xFF1E2E2B))
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("GASTOS", color = Color(0xFF6B7A7A))
                    Text("INGRESOS", color = Color(0xFF6B7A7A))
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${String.format("%.0f", uiState.expensesTotal)} $", color = Color(0xFFD14B4B))
                    Text("${String.format("%.0f", uiState.incomeTotal)} $", color = Color(0xFF1B9D5B))
                }

                Chart(uiState)

                uiState.expenseCategories
                    .sortedByDescending { it.amount }
                    .take(4)
                    .forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color(item.color), shape = RoundedCornerShape(50))
                                )
                                Text(item.name)
                            }
                            Text("${String.format("%.0f", item.amount)} $")
                        }
                    }
            }
        }
    }
}

@Composable
private fun Chart(uiState: BudgetUiState) {
    val total = uiState.expensesTotal.coerceAtLeast(1.0)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = -90f
            if (uiState.expenseCategories.isNotEmpty()) {
                uiState.expenseCategories.forEach { item ->
                    val sweep = ((item.amount / total).toFloat().coerceAtLeast(0.05f)) * 360f
                    drawArc(
                        color = Color(item.color),
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = true
                    )
                    startAngle += sweep
                }
            }
        }

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(Color(0xFFE9EAE6), shape = RoundedCornerShape(100))
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${String.format("%.0f", uiState.balance)} $", color = Color(0xFF1E2E2B))
        }
    }
}
