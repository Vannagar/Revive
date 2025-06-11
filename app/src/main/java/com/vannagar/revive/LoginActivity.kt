package com.vannagar.revive

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vannagar.revive.ui.theme.ReviveTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPrefs = getSharedPreferences("REVIVE_LAUNCHER_INFO", Context.MODE_PRIVATE)
        val password = sharedPrefs.getString("PASSWORD", null)
        val passwordIsSet = password != null
        setContent {
            ReviveTheme {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Column (
                        modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(horizontal = 25.dp, vertical = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(50.dp)
                    ) {
                        var input by remember { mutableStateOf(TextFieldValue("")) }
                        Text(
                            if (passwordIsSet)
                                "Are you sure you want to do this?"
                            else
                                "Set a tedious to type password\nOR\nHave someone else type a secret password",
                            color = Color.White,
                            fontSize = 25.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            fontWeight = FontWeight.Thin,
                            lineHeight = 30.sp
                        )
                        TextField(
                            value = input,
                            onValueChange = { input = it },
                        )
                        FilledTonalButton(
                            onClick = {
                                if (passwordIsSet) {
                                    if (input.text == password) {
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                CustomizeActivity::class.java
                                            )
                                        )
                                    } else {
                                        input = TextFieldValue("I don't think you are")
                                    }
                                } else {
                                    sharedPrefs.edit().putString("PASSWORD", input.text).apply()
                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            CustomizeActivity::class.java
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.wrapContentWidth().wrapContentHeight()
                        ){
                            Text(
                                if (passwordIsSet)
                                    "I'm sure"
                                else
                                    "Revive"
                                , color = Color.White, fontWeight = FontWeight.Thin, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}