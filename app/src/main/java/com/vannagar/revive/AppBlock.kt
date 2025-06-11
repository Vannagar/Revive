package com.vannagar.revive

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap

data class AppBlock (val name:String, val icon: Drawable, val packageName:String, var toggle: Boolean = false)

@Composable
fun AppList(appList: List<AppBlock>, context: Context){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        LazyColumn(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .wrapContentHeight()
                .fillMaxWidth(0.75f),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(25.dp)
        ) {
            item { Spacer(modifier = Modifier.height(10.dp)) }
            items(appList) {
                AppItem(it, context)
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
val getAppBlockOnClick:(AppBlock, Context) -> (() -> Unit) = { app, context ->
    {
        if(app.packageName == "com.vannagar.revive"){
            context.startActivity(Intent(context, LoginActivity::class.java))
        } else {
            context.startActivity(context.packageManager.getLaunchIntentForPackage(app.packageName))
        }
    }
}

@Composable
fun AppItem(appBlock: AppBlock, context: Context){
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .clickable(onClick = getAppBlockOnClick(appBlock, context)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(25.dp)
    ){
        Image(appBlock.icon.toBitmap().asImageBitmap(), contentDescription = appBlock.name,
            modifier = Modifier.size(40.dp))
        Text(appBlock.name.lowercase(), fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Thin, color = White)
    }
}
