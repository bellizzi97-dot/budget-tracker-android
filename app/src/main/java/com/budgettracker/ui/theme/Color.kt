package com.budgettracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.budgettracker.data.model.CategoryType
import com.budgettracker.ui.BudgetUiState

@Composable
fun BudgetsScreen(
    uiState: BudgetUiState,
    onAddBudget: (Long, Double) -> Unit,
) {
    var limitText by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf(uiState.categories.firstOrNull { it.type == CategoryType.EXPENSE }?.id ?: 1L) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E7C67))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Presupuestos", color = Color.White, style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Añadir presupuesto")
        }

        uiState.budgets.forEach { budget ->
            val category = uiState.categories.firstOrNull { it.id == budget.categoryId }
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(category?.name ?: "Sin categoría")
                    Text("Límite: ${String.format("%.0f", budget.limit)} $")
                }
            }
        }
    }

    if (showDialog) {
        Card(modifier = Modifier.fillMaxWidth().padding(24.dp), shape = RoundedCornerShape(20.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nuevo presupuesto")
                val expenseCategories = uiState.categories.filter { it.type == CategoryType.EXPENSE }
                if (expenseCategories.isNotEmpty()) {
                    selectedCategoryId = expenseCategories.first().id
                }
                OutlinedTextField(value = limitText, onValueChange = { limitText = it }, label = { Text("Monto límite") }, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { showDialog = false }) { Text("Cancelar") }
                    Button(
                        onClick = {
                            val parsed = limitText.toDoubleOrNull() ?: 0.0
                            if (parsed > 0.0) {
                                onAddBudget(selectedCategoryId, parsed)
                                limitText = ""
                                showDialog = false
                            }
                        }
                    ) { Text("Guardar") }
                }
            }
        }
    }
}
