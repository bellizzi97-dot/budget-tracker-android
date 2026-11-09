package com.budgettracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.budgettracker.data.model.CategoryType
import com.budgettracker.data.model.TransactionType
import com.budgettracker.ui.BudgetUiState

@Composable
fun TransactionsScreen(
    uiState: BudgetUiState,
    onAddTransaction: (String, Double, TransactionType, Long) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
    var selectedCategoryId by remember { mutableStateOf(uiState.categories.firstOrNull { it.type == selectedType }?.id ?: 1L) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E7C67))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Operaciones", color = Color.White, style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5B729))
        ) {
            Text("Añadir operación", color = Color.Black)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.transactions.sortedByDescending { it.date }) { transaction ->
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(transaction.title)
                            Text("${transaction.date}", color = Color.Gray)
                        }
                        Text(
                            text = "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"}${String.format("%.0f", transaction.amount)} $",
                            color = if (transaction.type == TransactionType.EXPENSE) Color(0xFFD14B4B) else Color(0xFF1B9D5B)
                        )
                    }
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
                Text("Nueva operación")
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = amountText, onValueChange = { amountText = it }, label = { Text("Monto") }, modifier = Modifier.fillMaxWidth())

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { selectedType = TransactionType.EXPENSE }) { Text("Gasto") }
                    OutlinedButton(onClick = { selectedType = TransactionType.INCOME }) { Text("Ingreso") }
                }

                val categoryOptions = uiState.categories.filter { it.type == CategoryType.EXPENSE && selectedType == TransactionType.EXPENSE || it.type == CategoryType.INCOME && selectedType == TransactionType.INCOME }
                if (categoryOptions.isNotEmpty()) {
                    selectedCategoryId = categoryOptions.first().id
                    Text("Categoría: ${categoryOptions.first().name}")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { showDialog = false }) { Text("Cancelar") }
                    Button(
                        onClick = {
                            val parsed = amountText.toDoubleOrNull() ?: 0.0
                            if (title.isNotBlank() && parsed > 0.0) {
                                onAddTransaction(title, parsed, selectedType, selectedCategoryId)
                                title = ""
                                amountText = ""
                                showDialog = false
                            }
                        }
                    ) { Text("Guardar") }
                }
            }
        }
    }
}
