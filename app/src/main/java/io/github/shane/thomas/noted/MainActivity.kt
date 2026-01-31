package io.github.shane.thomas.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import io.github.shane.thomas.noted.ui.screens.EditorScreen
import io.github.shane.thomas.noted.ui.screens.HomeScreen
import io.github.shane.thomas.noted.ui.theme.NotedTheme

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        analytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotedTheme {

                SharedTransitionLayout() {
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
                                },
                                this@SharedTransitionLayout,
                                this@composable
                            )
                        }

                        composable("editor") {
                            EditorScreen(
                                onBackClick = {
                                    navController.navigate("home"){
                                        popUpTo("editor") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
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
