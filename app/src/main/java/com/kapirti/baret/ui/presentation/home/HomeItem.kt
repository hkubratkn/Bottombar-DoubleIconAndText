package com.kapirti.baret.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kapirti.baret.common.composable.NoSurfaceImage
import com.kapirti.baret.model.Asset
import com.kapirti.baret.R.string as AppText

private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun HomeItem(
    asset: Asset,
    onAssetClick: (Asset) -> Unit
){
    Column(modifier = HzPadding) {
        NoSurfaceImage(imageUrl = asset.photo, contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onAssetClick(asset) }
        )
        Row (modifier = Modifier.fillMaxWidth()){
            Text(text = asset.title, color = MaterialTheme.colors.primary, modifier = Modifier.weight(1f))
            Text(text = asset.price, color = MaterialTheme.colors.error)
        }

        Spacer(Modifier.height(16.dp))
        var seeMore by remember { mutableStateOf(true) }
        Text(
            text = asset.description,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            maxLines = if (seeMore) 5 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
        )
        val textButton = if (seeMore) {
            stringResource(id = AppText.see_more)
        } else {
            stringResource(id = AppText.see_less)
        }
        Text(
            text = textButton,
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .heightIn(20.dp)
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clickable {
                    seeMore = !seeMore
                }
        )
        Spacer(Modifier.height(6.dp))
    }
}