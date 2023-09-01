package com.zenika.dragndrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.zenika.dragndrop.ui.theme.DragnDropTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragnDropTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    StartNStop()
                }
            }
        }
    }
}