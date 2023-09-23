package com.kapirti.baret.ui.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.kapirti.baret.common.EmptyContent
import com.kapirti.baret.common.composable.AdsBannerToolbar
import com.kapirti.baret.common.composable.BaretSurface
import com.kapirti.baret.common.composable.JumpToButton
import com.kapirti.baret.common.composable.LoadingContent
import com.kapirti.baret.core.constants.Constants.ADS_HOME_BANNER_ID
import com.kapirti.baret.model.Asset
import com.kapirti.baret.model.BaretRepo
import kotlinx.coroutines.launch
import com.kapirti.baret.R.string as AppText

@Composable
internal fun HomeRoute(
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit, ) { HomeScreen(openScreen = openScreen, restartApp = restartApp) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
//    includeAssetViewModel: IncludeAssetViewModel,
//    onSnackClick: (Long) -> Unit,
    //  onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val filters = remember { BaretRepo.getCitys() }

    Scaffold(
        topBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
        modifier = modifier
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AssetContent(
            loading = uiState.isLoading,
            assets = uiState.items,
            filters = filters,
            onRefresh = viewModel::refresh,
            selectedAnswer = viewModel.city,
            onOptionSelected = {
                viewModel.onCityChange(restartApp = restartApp, it)
            },
            selectedAnswerRentSell = viewModel.rentSell,
            onOptionSelectedRentSell = {
                viewModel.onRentSellChange(restartApp = restartApp, it)
            },
            selectedAnswerType = viewModel.type,
            onOptionSelectedType = {
                viewModel.onTypeChange(restartApp = restartApp, it)
            },
            onAssetClick = {},
            modifier = Modifier.padding(paddingValues)
            /**            snackCollections,
            onSnackClick,
            onTaskClick = onTaskClick,
             */
        )
    }
}

@Composable
private fun AssetContent(
    loading: Boolean,
    assets: List<Asset>,
    filters: List<Int>,
    onRefresh: () -> Unit,
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    selectedAnswerRentSell: String?,
    onOptionSelectedRentSell: (String) -> Unit,
    selectedAnswerType: String?,
    onOptionSelectedType: (String) -> Unit,
    onAssetClick: (Asset) -> Unit,
    modifier: Modifier = Modifier
) {
    BaretSurface(modifier = modifier.fillMaxSize()) {
        Box {
            AssetList(loading, assets, filters, onRefresh,
                selectedAnswer = selectedAnswer,
                onOptionSelected = onOptionSelected,
                selectedAnswerRentSell = selectedAnswerRentSell,
                onOptionSelectedRentSell = onOptionSelectedRentSell,
                selectedAnswerType = selectedAnswerType,
                onOptionSelectedType = onOptionSelectedType,
                onAssetClick = onAssetClick)
        }
    }
}

@Composable
private fun AssetList(
    loading: Boolean,
    assets: List<Asset>,
    filters: List<Int>,
    onRefresh: () -> Unit,
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    selectedAnswerRentSell: String?,
    onOptionSelectedRentSell: (String) -> Unit,
    selectedAnswerType: String?,
    onOptionSelectedType: (String) -> Unit,
    onAssetClick: (Asset) -> Unit,
    modifier: Modifier = Modifier,
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection(),
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Column {
        FilterBar(
            filters,
            selectedAnswer = selectedAnswer,
            onOptionSelected = onOptionSelected,
            onShowFilters = { filtersVisible = true }
        )

        LoadingContent(
            loading = loading,
            empty = assets.isEmpty() && !loading,
            emptyContent = { EmptyContent(AppText.assets_not_found, Icons.Default.DirectionsCar, modifier) },
            onRefresh = onRefresh
        ) {

            BoxWithConstraints(modifier = modifier.nestedScroll(nestedScrollInteropConnection)) {
                LazyColumn {
                    /**
                    item {
                    Spacer(
                    Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    )
                    )
                    FilterBar(filters,
                    onShowFilters = { filtersVisible = true }
                    )
                    }*/
                    items(assets, key = { it.uid }) { assetItem ->
                        HomeItem(
                            asset = assetItem,
                            onAssetClick = onAssetClick
                        )
                    }
                }

                /**                    SnackCollection(
                snackCollection = snackCollection,
                onSnackClick = onSnackClick,
                index = index
                )*/
                val jumpThreshold = with(LocalDensity.current) { JumpToTopThreshold.toPx() }
                val jumpToTopButtonEnabled = remember {
                    derivedStateOf {
                        scrollState.firstVisibleItemIndex != 0 ||
                                scrollState.firstVisibleItemScrollOffset > jumpThreshold
                    }
                }

                JumpToButton(
                    text = AppText.jump_top,
                    icon = Icons.Default.ArrowUpward,
                    enabled = jumpToTopButtonEnabled.value,
                    onClicked = {
                        scope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false },
            selectedAnswerRentSell = selectedAnswerRentSell,
            onOptionSelectedRentSell = onOptionSelectedRentSell,
            selectedAnswerType = selectedAnswerType,
            onOptionSelectedType = onOptionSelectedType,
        )
    }
}

/**
@Composable
private fun TaskItem(
task: Task,
onCheckedChange: (Boolean) -> Unit,
onTaskClick: (Task) -> Unit
) {
Row(
verticalAlignment = Alignment.CenterVertically,
modifier = Modifier
.fillMaxWidth()
.padding(
horizontal = dimensionResource(id = AppDimen.horizontal_margin),
vertical = dimensionResource(id = AppDimen.list_item_padding),
)
.clickable { onTaskClick(task) }
) {
Checkbox(
checked = task.isCompleted,
onCheckedChange = onCheckedChange
)
Text(
text = task.titleForList,
style = MaterialTheme.typography.h6,
modifier = Modifier.padding(
start = dimensionResource(id = AppDimen.horizontal_margin)
),
textDecoration = if (task.isCompleted) {
TextDecoration.LineThrough
} else {
null
}
)
}
}*/
/**

SnackCollectionList(
filters = filters,
assets = assets.value,
state = scrollState,
selectedAnswer = viewModel.city,
onOptionSelected = viewModel::onCityChange,
onToggleFavorite = { viewModel.onSaveClick(it) },
onAssetClick = {
assetToDetailViewModel.onAssetChange(it)
openScreen(ASSET_DETAIL_SCREEN)
},
modifier = Modifier.padding(innerPadding)
)
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SnackCollectionList(
filters: List<Int>,
assets: List<Asset>,
state: LazyListState,
selectedAnswer: Int?,
onOptionSelected: (Int) -> Unit,
onToggleFavorite: (Asset) -> Unit,
onAssetClick: (Asset ) -> Unit,

Box(modifier) {
LazyColumn(state = state) {

item {
/**                Spacer(
Modifier.windowInsetsTopHeight(
WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
)
)*/

items(assets, key = { it.uid }) { assetItem ->
AssetItem(
asset = assetItem,
isFavorite = false,
onToggleFavorite = { onToggleFavorite(assetItem)},
onAssetClick = {onAssetClick(assetItem)}
)
}
}
 */




/**
val scrollState = rememberLazyListState()
val scope = rememberCoroutineScope()
val assets = viewModel.assets.collectAsStateWithLifecycle(emptyList())

BoxWithConstraints(modifier = modifier.fillMaxSize().nestedScroll(nestedScrollInteropConnection).systemBarsPadding()) {
SnackCollectionList(
filters = filters,
assets = assets.value,
state = scrollState,
selectedAnswer = viewModel.city,
onOptionSelected = viewModel::onCityChange,
onToggleFavorite = { viewModel.onSaveClick(it) },
onAssetClick = {
assetToDetailViewModel.onAssetChange(it)
openScreen(ASSET_DETAIL_SCREEN)
},
modifier = Modifier.padding(innerPadding)
)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SnackCollectionList(
filters: List<Int>,
assets: List<Asset>,
state: LazyListState,
selectedAnswer: Int?,
onOptionSelected: (Int) -> Unit,
onToggleFavorite: (Asset) -> Unit,
onAssetClick: (Asset ) -> Unit,

private enum class TabPage {
RENT, SELL
}
 */
private val JumpToTopThreshold = 56.dp