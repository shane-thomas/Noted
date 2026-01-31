package io.github.shane.thomas.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import io.github.shane.thomas.noted.ui.screens.EditorScreen
import io.github.shane.thomas.noted.ui.screens.HomeScreen
import io.github.shane.thomas.noted.ui.theme.NotedTheme
import io.github.shane.thomas.noted.ui.viewmodel.EditorViewModel

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        analytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: EditorViewModel = viewModel()
            NotedTheme {

                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "editor",
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onFabClick = {
                                    navController.navigate("editor")
                                    viewModel.updateText("")
                                },
                                this@SharedTransitionLayout,
                                this@composable
                            )
                        }

                        composable("editor") {
                            EditorScreen(
                                onBackClick = {
                                    navController.navigate("home") {
                                        popUpTo("editor") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                viewModel = viewModel,
                                this@SharedTransitionLayout,
                                this@composable
                            )
                        }
                    }
                }
            }
        }
    }
}
