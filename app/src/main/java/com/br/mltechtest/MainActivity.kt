package com.br.mltechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.br.mltechtest.core.navigation.Navigation
import com.br.mltechtest.core.designsystem.theme.MLTechTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MLTechTestTheme {
                setContent {
                    val navController = rememberNavController()
                    Navigation(navController)
                }
            }
        }
    }
}