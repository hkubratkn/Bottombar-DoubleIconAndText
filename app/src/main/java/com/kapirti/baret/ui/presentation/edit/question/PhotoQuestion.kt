package com.kapirti.baret.ui.presentation.edit.question

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.kapirti.baret.R.string as AppText
import com.kapirti.baret.R.drawable as AppIcon
import com.kapirti.baret.ui.presentation.edit.QuestionWrapper

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoQuestion(
    @StringRes titleResourceId: Int,
    imageUri: Uri?,
    onPhotoTaken: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasPhoto = imageUri != null
    val iconResource = if (hasPhoto) {
        Icons.Filled.SwapHoriz
    } else {
        Icons.Filled.AddAPhoto
    }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { success ->
            onPhotoTaken(success!!)
        }
    )


    QuestionWrapper(
        titleResourceId = titleResourceId,
        modifier = modifier,
    ) {

        PermissionRequired(
            permissionState = cameraPermissionState,
            permissionNotGrantedContent = {
                OutlinedButton(
                    onClick = {
                        cameraPermissionState.launchPermissionRequest()
                    },
                    shape = MaterialTheme.shapes.small,
                    contentPadding = PaddingValues()
                ) {
                    PhotoDefaultImage(
                        modifier = Modifier.padding(
                            horizontal = 86.dp,
                            vertical = 74.dp
                        )
                    )
                }
            },
            permissionNotAvailableContent = {
                Text(text = "please enable camera functionalty from the settings")
            }
        ) {
            OutlinedButton(
                onClick = {
                    launcher.launch("image/*")
                },
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues()
            ) {
                Column {
                    if (hasPhoto) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(96.dp)
                                .aspectRatio(4 / 3f)
                        )
                    } else {
                        PhotoDefaultImage(
                            modifier = Modifier.padding(
                                horizontal = 86.dp,
                                vertical = 74.dp
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.BottomCenter)
                            .padding(vertical = 26.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = iconResource, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(
                                id = if (hasPhoto) {
                                    AppText.retake_photo
                                } else {
                                    AppText.add_photo
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotoDefaultImage(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        AppIcon.ic_selfie_light
    } else {
        AppIcon.ic_selfie_dark
    }
    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}