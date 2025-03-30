package com.example.revive

import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap

data class AppBlock (val name:String, val icon: Drawable, val packageName:String)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppList(appList: List<AppBlock>){
    val sortedAppList = appList.sortedWith(compareBy<AppBlock> {
        val key = it.name.lowercase()
        if(it.name.equals("Revive"))
            return@compareBy ""
        else
        return@compareBy key
    })
    LazyColumn(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .wrapContentHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(25.dp)
    ){
        item{Spacer(modifier = Modifier.height(25.dp))}
        items(sortedAppList) {
            AppItem(it)
        }
        item{Spacer(modifier = Modifier.height(25.dp))}
    }
}

@Composable
fun AppItem(appBlock: AppBlock){
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min).fillMaxWidth(0.75f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(25.dp)
    ){
        Image(appBlock.icon.toBitmap().asImageBitmap(), contentDescription = appBlock.name,
            modifier = Modifier.size(25.dp))
        Text(appBlock.name.lowercase(), fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Thin, color = White)
    }
}
