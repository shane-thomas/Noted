package io.github.shane.thomas.noted.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.shane.thomas.noted.R
import io.github.shane.thomas.noted.ui.components.EditorToolbar
import io.github.shane.thomas.noted.ui.theme.NotedTheme
import io.github.shane.thomas.noted.ui.viewmodel.EditorViewModel

@Composable
fun EditorScreen(
    onBackClick: () -> Unit,
    viewModel: EditorViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val expanded = animatedVisibilityScope.transition.isRunning
    val title by viewModel.title.collectAsState()
    val text by viewModel.text.collectAsState()

    with(sharedTransitionScope) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface,
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
                    RoundedCornerShape(if (expanded) 0.dp else 28.dp)
                ),
            topBar = { EditorTopBar(onBackClick = onBackClick) }
        ) { innerPadding ->
            EditorContent(
                title = title,
                text = text,
                onTitleChange = viewModel::updateTitle,
                onTextChange = viewModel::updateText,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
fun EditorContent(
    title: String,
    text: String,
    onTitleChange: (String) -> Unit,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BasicTextField(
                value = title,
                onValueChange = onTitleChange,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Body
            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.fillMaxSize()
            )
        }

        EditorToolbar(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ), navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "Back"
                )
            }
        }, title = {
            Text("noted!")
        }, actions = {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                    TooltipAnchorPosition.Above
                ),
                tooltip = { PlainTooltip { Text("Add to favorites") } },
                state = rememberTooltipState(),
            ) {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        painterResource(R.drawable.keep_icon),
                        contentDescription = "Pin note",
                    )
                }
            }
        })

}


@Preview
@Composable
private fun EditorTopBarPrev() {
    EditorTopBar(
        onBackClick = {  }
    )
}

@Preview(showBackground = true)
@Composable
private fun EditorContentPreview() {
    NotedTheme {
        EditorContent(
            text = "Preview text\nWrite anything here…",
            onTextChange = {},
            title = "Title test",
            onTitleChange = {},
        )
    }
}
