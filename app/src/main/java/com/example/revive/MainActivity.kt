package com.example.revive

import android.annotation.SuppressLint
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
    private lateinit var retrievedApplist: List<ApplicationInfo>
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        retrievedApplist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList = ArrayList<AppBlock>()

        for(app in retrievedApplist){
            if(app.flags and (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0){
             val appBlock = AppBlock(
                 app.loadLabel(packageManager).toString(),
                 app.loadIcon(packageManager),
                 app.packageName
             )
             appList.add(appBlock)
         }
        }

        Log.d("appList",retrievedApplist.size.toString())

        setContent {
            ReviveTheme {
                AppList(appList)
            }
        }
    }
}