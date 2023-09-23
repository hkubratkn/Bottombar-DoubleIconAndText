package com.kapirti.baret.ui.presentation.add

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.kapirti.baret.common.composable.AdsBannerToolbar
import com.kapirti.baret.common.composable.RegularCardEditor
import com.kapirti.baret.common.ext.card
import com.kapirti.baret.common.ext.smallHeightSpacer
import com.kapirti.baret.common.ext.smallWidthSpacer
import com.kapirti.baret.core.constants.Constants.ADS_ADD_BANNER_ID
import com.kapirti.baret.ui.presentation.profile.type.Authantication
import com.kapirti.baret.R.string as AppText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen (
    openScreen: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: AddViewModel = hiltViewModel(),
) {
    var showLocalShippingDialog by remember { mutableStateOf(false) }
    var showWorkMachineDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = { AdsBannerToolbar(ads = ADS_ADD_BANNER_ID) }) { innerPadding ->
        val modifier = modifier.padding(innerPadding)

        if (!viewModel.hasUser) {
            Authantication(
                onLogInClick = { viewModel.onLogInClick(openScreen) },
                onCreateClick = { viewModel.onCreateClick(openScreen) },
                modifier = modifier
            )
        } else {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                RegularCardEditor(AppText.car, Icons.Default.DirectionsCar, "", Modifier.card()){ viewModel.onCarSellClick(openScreen = openScreen) }
                Spacer(modifier = Modifier.smallHeightSpacer())
                AssetInside(AppText.local_shipping, Icons.Default.LocalShipping, showLocalShippingDialog,
                    onClick = { showLocalShippingDialog = !showLocalShippingDialog },
                    onSellClick = { viewModel.onLocalShippingSellClick(openScreen = openScreen) },
                    onRentClick = { viewModel.onLocalShippingRentClick(openScreen = openScreen) },
                )
                Spacer(modifier = Modifier.smallHeightSpacer())
                AssetInside(AppText.work_machine, Icons.Default.LocalShipping, showWorkMachineDialog,
                    onClick = { showWorkMachineDialog = !showWorkMachineDialog },
                    onSellClick = { viewModel.onWorkMachineSellClick(openScreen = openScreen) },
                    onRentClick = { viewModel.onWorkMachineRentClick(openScreen = openScreen) },
                )
            }
        }
    }
}

@Composable
private fun AssetInside(
    @StringRes text: Int,
    icon: ImageVector,
    showDialog: Boolean,
    onClick: () -> Unit,
    onSellClick: () -> Unit,
    onRentClick: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.card()
    ){
        RegularCardEditor(text, icon, "", Modifier, onClick)
        if(showDialog){
            RentSell(
                onSellClick = onSellClick,
                onRentClick = onRentClick
            )
        }
    }
}

@Composable
private fun RentSell(
    onSellClick: () -> Unit,
    onRentClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
    ){
        Button(onClick = onSellClick){Text(stringResource(id = AppText.sell))}
        Spacer(modifier = Modifier.smallWidthSpacer())
        Button(onClick = onRentClick){Text(stringResource(id = AppText.rent))}
    }
}





/**
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.baret.common.composable.VerticalGrid
import kotlin.math.max
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.res.stringResource
import com.kapirti.baret.R.string as AppText
import com.kapirti.baret.common.composable.RegularCardEditor
import com.kapirti.baret.common.ext.card
import com.kapirti.baret.core.data.local.SearchCategory
import com.kapirti.baret.core.data.local.SearchCategoryCollection
import com.kapirti.baret.core.data.local.searchCategoryCollections
import com.kapirti.baret.ui.presentation.profile.type.Authantication

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddScreen (
openScreen: (String) -> Unit,
modifier: Modifier = Modifier,
viewModel: AddViewModel = hiltViewModel(),
) {
val categories: List<SearchCategoryCollection> = searchCategoryCollections

Scaffold(
topBar = {}
) { innerPadding ->
val modifier = modifier.padding(innerPadding)

if (!viewModel.hasUser) {
Authantication(
onLogInClick = { viewModel.onLogInClick(openScreen) },
onCreateClick = { viewModel.onCreateClick(openScreen) },
modifier = modifier
)
} else {
LazyColumn {
itemsIndexed(categories) { index, collection ->
SearchCategoryCollection(collection, index,
onClick = {
viewModel.onClick(searchCategory = it, openScreen)
}
)
}
}
Spacer(Modifier.height(8.dp))
}
}

if (viewModel.showDialog) {
AlertDialog(
title = {
Column(){
RegularCardEditor(AppText.rent, Icons.Default.ShoppingCart, "", Modifier.card()) { viewModel.onRentClick(openScreen)}
RegularCardEditor(AppText.sell, Icons.Default.ShoppingCart, "", Modifier.card()) { viewModel.onSellClick(openScreen)}
} },
text = { },
dismissButton = { },
confirmButton = {},
onDismissRequest = { }
)
}
}

@Composable
private fun SearchCategoryCollection(
collection: SearchCategoryCollection,
index: Int,
modifier: Modifier = Modifier,
onClick: (SearchCategory) -> Unit
) {
Column(modifier) {
Text(
text = collection.name,
style = MaterialTheme.typography.h6,
color = Color.Yellow, //JetsnackTheme.colors.textPrimary,
modifier = Modifier
.heightIn(min = 56.dp)
.padding(horizontal = 24.dp, vertical = 4.dp)
.wrapContentHeight()
)
VerticalGrid(Modifier.padding(horizontal = 16.dp)) {
val gradient = when (index % 2) {
0 -> listOf(Color.Green, Color.Cyan)//JetsnackTheme.colors.gradient2_2
else -> listOf(Color.Yellow, Color.Blue) //JetsnackTheme.colors.gradient2_3
}
collection.categories.forEach { category ->
SearchCategory(
category = category,
gradient = gradient,
modifier = Modifier.padding(8.dp),
onClick = onClick
)
}
}
Spacer(Modifier.height(4.dp))
}
}

private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f

@Composable
private fun SearchCategory(
category: SearchCategory,
gradient: List<Color>,
modifier: Modifier = Modifier,
onClick: (SearchCategory) -> Unit
) {
Layout(
modifier = modifier
.aspectRatio(1.45f)
.shadow(elevation = 3.dp, shape = CategoryShape)
.clip(CategoryShape)
.background(Brush.horizontalGradient(gradient))
.clickable { onClick(category) },
content = {
Text(
text = stringResource(category.name),
style = MaterialTheme.typography.subtitle1,
color = Color.Red, //JetsnackTheme.colors.textSecondary,
modifier = Modifier
.padding(4.dp)
.padding(start = 8.dp)
)
Icon(
imageVector = category.imageUrl,
contentDescription = null,
modifier = Modifier.size(40.dp)
)
}
) { measurables, constraints ->
// Text given a set proportion of width (which is determined by the aspect ratio)
val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

// Image is sized to the larger of height of item, or a minimum value
// i.e. may appear larger than item (but clipped to the item bounds)
val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))
layout(
width = constraints.maxWidth,
height = constraints.minHeight
) {
textPlaceable.placeRelative(
x = 0,
y = (constraints.maxHeight - textPlaceable.height) / 2 // centered
)
imagePlaceable.placeRelative(
// image is placed to end of text i.e. will overflow to the end (but be clipped)
x = textWidth,
y = (constraints.maxHeight - imagePlaceable.height) / 2 // centered
)
}
}
}*/