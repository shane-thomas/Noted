package io.github.shane.thomas.noted.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.shane.thomas.noted.R
import io.github.shane.thomas.noted.ui.components.EditorToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val textState = remember { mutableStateOf("Testing Editor Screen") }
    val expanded = animatedVisibilityScope.transition.isRunning


    with(sharedTransitionScope) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
                .fillMaxSize()
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
                )
                .clip(
                    RoundedCornerShape(
                        if (expanded) 0.dp else 28.dp
                    )
                ),
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Back"
                        )
                    }
                })
            }) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                BasicTextField(
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )

                EditorToolbar(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}