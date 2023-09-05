package com.kapirti.baret
/**
import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.play.core.review.ReviewManagerFactory
import com.kapirti.baret.core.data.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaretApp(networkMonitor = networkMonitor)
            inAppReview(this)
        }
    }
}

/**
package com.kapirti.baret

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.play.core.review.ReviewManagerFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint
import com.kapirti.baret.core.data.network.NetworkMonitor
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
class MainActivity :   {


    private val viewModel: MainViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.saveIsOnline()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveLastSeen()
    }
}
*/
private fun inAppReview(context: Context) {
    val manager = ReviewManagerFactory.create(context)
    val request = manager.requestReviewFlow()

    request.addOnCompleteListener { request ->
        if (request.isSuccessful) {
            val reviewInfo = request.result
            val flow = manager.launchReviewFlow(context as Activity, reviewInfo!!)
            flow.addOnCompleteListener { _ -> }
        }
    }
}
*/

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.kapirti.baret.core.data.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NiaApp(networkMonitor = networkMonitor,)
        }
    }
}