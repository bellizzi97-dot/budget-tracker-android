package com.budgettracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.budgettracker.data.model.Category
import com.budgettracker.data.model.CategoryType

@Composable
fun CategoriesScreen(
    categories: List<Category>,
    onAddCategory: (String, CategoryType, Long) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(CategoryType.EXPENSE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E7C67))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Categorías",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Crear nueva categoría")
        }

        LazyColumn {
            items(categories) { category ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(category.name)
                        Text(category.type.name)
                    }
                }
            }
        }
    }

    if (showDialog) {
        Card(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nueva categoría")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { selectedType = CategoryType.EXPENSE }) {
                        Text("Gasto")
                    }
                    OutlinedButton(onClick = { selectedType = CategoryType.INCOME }) {
                        Text("Ingreso")
                    }
                }
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onAddCategory(name, selectedType, 0xFF4CAF50)
                            name = ""
                            showDialog = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
