package com.kapirti.baret.core.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kapirti.baret.core.constants.Constants.SHARE_CODE
import com.kapirti.baret.R.string as AppText

class SettingsRepository(private val context: Context) {
    fun share(){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, SHARE_CODE)
        intent.type = "text/plain"
        context.startActivity(Intent.createChooser(intent, context.getString(AppText.app_name)))
    }

    fun rate(){
        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SHARE_CODE))
        context.startActivity(shareIntent)
    }
}