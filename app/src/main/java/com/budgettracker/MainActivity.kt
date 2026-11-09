package com.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.budgettracker.ui.BudgetApp
import com.budgettracker.ui.theme.BudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetTheme {
                BudgetApp()
            }
        }
    }
}
