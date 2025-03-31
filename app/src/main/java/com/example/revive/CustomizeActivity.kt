package com.example.revive

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.revive.ui.theme.ReviveTheme

class CustomizeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPrefs = getSharedPreferences("REVIVE_LAUNCHER_INFO", Context.MODE_PRIVATE)
        val packageNameList = sharedPrefs.getStringSet("PKG_NAME_LIST", setOf(packageName))!!
        val realAppList = packageNameList.map{
            packageManager.getApplicationInfo(it, PackageManager.GET_META_DATA)
        }
        val sortedRealAppList = realAppList.sortedWith(compareBy<ApplicationInfo> {
            it.loadLabel(packageManager).toString().lowercase()
        })
        val appList = ArrayList<AppBlock>()
        val retrievedAppList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val sortedRetrievedAppList = retrievedAppList.sortedWith(compareBy<ApplicationInfo> {
            it.loadLabel(packageManager).toString().lowercase()
        })
        var ptr = 0
        for(app in sortedRetrievedAppList){
            var visibility = false
            while(ptr < sortedRealAppList.size){
                if(sortedRealAppList[ptr].packageName == app.packageName)
                    visibility = true
                if(sortedRealAppList[ptr].loadLabel(packageManager).toString().lowercase() >= app.loadLabel(packageManager).toString().lowercase())
                    break
                ptr++
            }
            val appBlock = AppBlock(
                app.loadLabel(packageManager).toString(),
                app.loadIcon(packageManager),
                app.packageName,
                visibility
            )
            appList.add(appBlock)
        }

        setContent {
            ReviveTheme {
                Row(
                    modifier = Modifier.padding(vertical = 25.dp).fillMaxWidth().zIndex(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
                ){
                    ElevatedButton(
                        onClick = {
                            sharedPrefs.edit().putStringSet(
                                "PKG_NAME_LIST",
                                appList.filter{
                                    it.toggle
                                }.map {
                                    it.packageName
                                }.toSet()
                            ).commit()
                            startActivity(Intent(this@CustomizeActivity, MainActivity::class.java))
                        },
                    ){
                        Text("Apply Changes")
                    }
                    ElevatedButton(
                        onClick = {
                            sharedPrefs.edit().putString(
                                "PASSWORD",null).commit()
                            startActivity(Intent(this@CustomizeActivity, FirstLaunchActivity::class.java))
                        },
                    ){
                        Text("Change Password")
                    }
                }
                AppSelector(appList, this)
            }
        }
    }
}