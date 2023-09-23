package com.kapirti.baret.ui.presentation.profile.type

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kapirti.baret.common.composable.SurfaceImage
import com.kapirti.baret.common.ext.baselineHeight
import com.kapirti.baret.common.ext.smallSpacer
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.R.string as AppText

@Composable
fun Done(
    profile: Profile?,
    displayName: String,
    containerHeight: Dp,
    onPhotoClick: () -> Unit,
    onDisplayNameClick: () -> Unit,
    onNameSurnameClick: () -> Unit,
    onContactClicked: () -> Unit = { },
    onDescriptionClicked: () -> Unit = { },
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(scrollState),) {
        Spacer(modifier = Modifier.smallSpacer())

        ProfileHeader(
            scrollState,
            profile,
            containerHeight,
            onPhotoClicked = onPhotoClick,
        )
        UserInfoFields(
            profile = profile, displayName = displayName, containerHeight,
            onDisplayNameClicked = onDisplayNameClick,
            onNameSurnameClicked = onNameSurnameClick,
            onContactClicked = onContactClicked,
            onDescriptionClicked = onDescriptionClicked
        )
    }
}

@Composable
private fun UserInfoFields(
    profile: Profile?, displayName: String, containerHeight: Dp,
    onDisplayNameClicked: () -> Unit = {},
    onNameSurnameClicked: () -> Unit = { },
    onContactClicked: () -> Unit = { },
    onDescriptionClicked: () -> Unit = { },
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        NameAndPosition(profile)

        ProfileProperty(stringResource(AppText.display_name), displayName){ onDisplayNameClicked()}
        ProfileProperty(stringResource(AppText.name_and_surname), profile?.let { "${it.namedb} ${it.surnamedb}"} ?: ""){ onNameSurnameClicked()}
        ProfileProperty(stringResource(AppText.contact), profile?.let{it.contactdb} ?: ""){ onContactClicked()}
        ProfileProperty(stringResource(AppText.description), profile?.let{ it.descriptiondb} ?: ""){ onDescriptionClicked()}

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun NameAndPosition(profile: Profile?) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Name(
            profile,
            modifier = Modifier.baselineHeight(32.dp)
        )
        Position(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .baselineHeight(24.dp)
        )
    }
}

@Composable
private fun Name(profile: Profile?, modifier: Modifier = Modifier) {
    Text(
        text = profile?.let{"${it.namedb} ${it.surnamedb}"} ?: "",
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun Position(modifier: Modifier = Modifier) {
    Text(
        text = "",
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun ProfileHeader(
    scrollState: ScrollState,
    profile: Profile?,
    containerHeight: Dp,
    onPhotoClicked: () -> Unit = { }
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    profile?.let {
        SurfaceImage(
            imageUrl = it.photodb,
            contentDescription = null,
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = offsetDp,
                    end = 16.dp
                )
                .clip(CircleShape),
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)) {
            Button(
                shape = MaterialTheme.shapes.extraSmall,
                onClick = onPhotoClicked
            ) {
                Text(stringResource(AppText.retake_photo))
            }
        }
    }
}

@Composable
private fun ProfileProperty(label: String, value: String, isLink: Boolean = false, onIconClick: () -> Unit = {}) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        Text(
            text = label,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val style = if (isLink) {
            MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
        } else {
            MaterialTheme.typography.bodyLarge
        }

        Row(
            modifier = Modifier
                .baselineHeight(24.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                style = style
            )
            Icon(
                Icons.Default.Edit,
                null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onIconClick() }
            )
        }
    }
}