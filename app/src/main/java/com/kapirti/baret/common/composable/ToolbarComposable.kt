package com.kapirti.baret.common.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdsBannerToolbar(ads: String) {
    AndroidView(
        factory = {
            val aview = AdView(it)
            aview.apply {
                aview.setAdSize(AdSize.BANNER)
                adUnitId = ads
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimaryContainer
}