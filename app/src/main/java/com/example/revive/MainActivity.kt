package com.example.revive

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.revive.ui.theme.ReviveTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPrefs = getSharedPreferences("REVIVE_LAUNCHER_INFO", Context.MODE_PRIVATE)
        val packageNameList = sharedPrefs.getStringSet("PKG_NAME_LIST", emptySet())!!
        if (packageNameList.isEmpty()) {
            startActivity(Intent(this, FirstLaunchActivity::class.java))
        }
        val appList = ArrayList<AppBlock>()
        val realAppList = packageNameList.map{
            packageManager.getApplicationInfo(it, PackageManager.GET_META_DATA)
        }
        for(app in realAppList){
             val appBlock = AppBlock(
                 app.loadLabel(packageManager).toString(),
                 app.loadIcon(packageManager),
                 app.packageName
             )
             appList.add(appBlock)
        }

        val sortedAppList = appList.sortedWith(compareBy<AppBlock> {
            val key = it.name.lowercase()
            if(it.name == "Revive")
                return@compareBy ""
            else
                return@compareBy key
        })

        setContent {
            ReviveTheme {
                AppList(sortedAppList, this)
            }
        }
    }
}