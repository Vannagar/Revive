package com.example.revive

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppSelector(appList: List<AppBlock>, context: Context){
    val sortedAppList = appList.sortedWith(compareBy<AppBlock> {it.name.lowercase()})
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 30.dp)
            .wrapContentHeight().fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ){
        item{ Spacer(modifier = Modifier.height(25.dp)) }
        items(sortedAppList) {
            AppSelection(it, context)
        }
    }
}

@Composable
fun AppSelection(appBlock: AppBlock, context: Context){
    var isToggled by remember { mutableStateOf(appBlock.toggle) }
    Row(modifier = Modifier
        .height(IntrinsicSize.Min).width(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(25.dp)){
        FilledIconToggleButton(
            checked = isToggled,
            onCheckedChange = { checked ->
                if (appBlock.packageName != context.packageName) {
                    appBlock.toggle = checked
                    isToggled = checked
                }
            },
            shape = IconButtonDefaults.filledShape,
            colors = IconButtonDefaults.filledIconToggleButtonColors(),
        ) {
            if(isToggled)
                Icon(Icons.Filled.Visibility, contentDescription = "App is selected")
            else
                Icon(Icons.Filled.VisibilityOff, contentDescription = "App is deselected")
        }
        AppItem(appBlock, context)
    }
}