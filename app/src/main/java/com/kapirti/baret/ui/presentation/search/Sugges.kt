package com.kapirti.baret.ui.presentation.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapirti.baret.common.composable.NoSurfaceImage
import com.kapirti.baret.core.room.recent.Recent
import com.kapirti.baret.R.string as AppText

@Composable
fun SearchSuggestions(
    suggestions: List<Recent>,
    onDeleteClick: (Recent) -> Unit,
    onSuggestionSelect: (String) -> Unit
) {
    LazyColumn {
        item {
            SuggestionHeader(stringResource(AppText.recent_searches))
        }
        items(suggestions, key = { it.id }) {recent ->
            Suggestion(
                recent = recent,
                onDeleteClick = onDeleteClick,
                onSuggestionSelect = onSuggestionSelect,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
        item {
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun SuggestionHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xffded6fe),
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}

@Composable
private fun Suggestion(
    recent: Recent,
    onDeleteClick: (Recent) -> Unit,
    onSuggestionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        NoSurfaceImage(
            imageUrl = recent.photo,
            contentDescription = recent.displayName,
            modifier = Modifier.padding(end = 10.dp).size(40.dp).clip(CircleShape)
        )
        Text(
            text = recent.displayName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
                .clickable { onSuggestionSelect(recent.displayName) }
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(24.dp).border(1.dp, Color.Red, CircleShape)
                .clickable { onDeleteClick(recent) }
        )
    }
}