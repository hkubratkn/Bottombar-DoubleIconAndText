package com.kapirti.baret.ui.presentation.user_profile
/**
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.kapirti.baret.R.string as AppText
import kotlin.math.max
import kotlin.math.min


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kapirti.baret.common.composable.BaretSurface
import com.kapirti.baret.common.composable.BasicDivider
import com.kapirti.baret.common.composable.SurfaceImage
import com.kapirti.baret.common.composable.arrowBackIcon
import com.kapirti.baret.core.view_model.IncludeUserViewModel
import com.kapirti.baret.model.User

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun UserProfileScreen(
    popUpScreen: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    includeUserViewModel: IncludeUserViewModel,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
    val user = includeUserViewModel.user
//    val profile = viewModel.profile.collectAsStateWithLifecycle(initialValue = null)
  //  val chatId = "${viewModel.uid}${user?.let { it.uid }}"
    val scrollState = rememberScrollState()
/**
    val userName = user?.let { it.name } ?: ""
    val userSurname = user?.let { it.surname } ?: ""
    val userPhoto = user?.let { it.photo } ?: ""
    val userUid = user?.let { it.uid } ?: ""

    val profileName = profile.value?.let { it.namedb } ?: ""
    val profileSurname = profile.value?.let { it.surnamedb } ?: ""
    val profilePhoto = profile.value?.let { it.photodb } ?: ""
*/

    BoxWithConstraints(
        modifier = modifier.fillMaxSize().nestedScroll(nestedScrollInteropConnection).systemBarsPadding()
    ) {
        Box(Modifier.fillMaxSize()) {
            val scroll = rememberScrollState(0)
            Header()
            Body(user, scroll)
            Title(user) { scroll.value }
            Image(user?.let { it.photo } ?: "photo") { scroll.value }
            Up(popUpScreen)
            CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
        }

        val fabExtended by remember { derivedStateOf { scrollState.value == 0 } }
     /**   FabW3(
            text = AppText.message,
            icon = Icons.Default.Chat,
            extended = fabExtended,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = ((-100).dp)),
            onFabClicked = {
                viewModel.onDoneClick(
//                    text = "Talk to me!", who = viewModel.uid, chatId = chatId, openAndPopUp = openAndPopUp,
  //                  partnerName = userName, partnerSurname = userSurname, partnerPhoto = userPhoto, partnerUid = userUid,
    //                profileName = profileName, profileSurname = profileSurname, profilePhoto = profilePhoto
                )
            }
        )*/
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(Color(0xFFa7a7a8), Color(0xFF647075))))
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Color(0xff121212).copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = arrowBackIcon(),
            tint = Color(0xffffffff),
            contentDescription = null
        )
    }
}

@Composable
private fun Body(
    user: User?,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            BaretSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(AppText.description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = user?.let{ it.description } ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0x99000000),
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    val textButton = if (seeMore) {
                        stringResource(id = AppText.see_more)
                    } else {
                        stringResource(id = AppText.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0xff005687),
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )
                    Spacer(Modifier.height(40.dp))

                    Spacer(Modifier.height(16.dp))
                    BasicDivider()
                    Spacer(Modifier.height(40.dp))
                    Text(
                        text = stringResource(AppText.join),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "",//user?.let { itUser -> itUser.date?.let{ itLast -> timeCustomFormat(itLast.seconds) } } ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )
/**
                    user?.let {
                        key(it.hobby) {
                            HobbyCollection(hobbys = it.hobby)
                        }
                    }
*/
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(user: User?, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    val timestamp = Timestamp.now()
//    val age = calculateBirthday(birthday =user?.let { it.birthday } ?: "", timestamp = timestamp)

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color(0xffffffff))
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "age",
            style = MaterialTheme.typography.displaySmall,
            color = Color(0xde000000),
            modifier = HzPadding
        )
        Text(
            text = "", //"${user?.let{ it.name }} ${user?.let { it.surname }}",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp,
            color = Color.Black,//Color(0xbdffffff),
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = user?.let{
/**                if(it.online){
                    stringResource(id = AppText.online)
                }else {
                    it.lastSeen?.let { itTime ->
                        timeCustomFormat(itTime.seconds)
                    }
                }*/
            } ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xffded6fe),
            modifier = HzPadding
        )

        Spacer(Modifier.height(8.dp))
        BasicDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        SurfaceImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun CartBottomBar(modifier: Modifier = Modifier) {
    BaretSurface(modifier) {
        Column {
            BasicDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(HzPadding)
                    .heightIn(min = BottomBarHeight)
            ) {
//                AdsBannerToolbar(ads = ADS_USER_PROFILE_BANNER_ID)
            }
        }
    }
}
*/
/**
@Composable
private fun HobbyCollection(
    hobbys: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = stringResource(AppText.in_my_free_time),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
        }
        Hobbys(hobbys)
    }
}

@Composable
private fun Hobbys(
    hobbys: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
    ) {
        items(hobbys) { hobby ->
            HobbyItem(hobby)
        }
    }
}

@Composable
private fun HobbyItem(
    hobby: String,
    modifier: Modifier = Modifier
) {
    IraSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = hobbyIcon(hobby),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = hobby,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}*/