package com.awaisakram.woltcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.awaisakram.woltcompose.presentation.search.SearchScreen
import com.awaisakram.woltcompose.presentation.theme.WoltComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WoltComposeTheme {
                SearchScreen()
            }
        }
    }
}