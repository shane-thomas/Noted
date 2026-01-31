package io.github.shane.thomas.noted.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.shane.thomas.noted.R

//@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0.dp),
        floatingActionButton = {
            with(sharedTransitionScope) {
                ExtendedFloatingActionButton(
                    text = { Text("Add note") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.add_icon),
                            contentDescription = "Add note"
                        )
                    },
                    onClick = onFabClick,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState("FAB_EXPLODE"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                            boundsTransform = { _, _ ->
                                tween(
                                    durationMillis = 300,
                                    easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
                                )
                            }
                        ),
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Your Notes")
        }
    }
}