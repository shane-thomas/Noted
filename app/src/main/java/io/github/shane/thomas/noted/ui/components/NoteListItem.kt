package io.github.shane.thomas.noted.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NoteListItem(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    ListItem(
        onClick = { },
        modifier = modifier.fillMaxWidth(),
        content = {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.titleLargeEmphasized
            )
        },
        supportingContent = {
            Text(
                text = content,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            supportingContentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Preview
@Composable
private fun NoteListItemPrev() {
    NoteListItem(
        title = "Welcome", content = "Hello world"
    )
}

@Preview(showBackground = true)
@Composable
private fun SwipeableNoteListPreview() {
    MaterialTheme {
        Surface {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(
                    listOf(
                        "Welcome" to "This is your first note.",
                        "Ideas" to "Build a notes app with Material 3.",
                        "Todos" to "• Buy milk\n• Finish Compose UI",
                        "Draft" to "Swipe left to delete, right to pin.",
                        "Thoughts" to "Keep-style interactions feel great."
                    )
                ) { (title, content) ->
                    NoteListItem(
                        title = title,
                        content = content
                    )
                }
            }
        }
    }
}
