package com.example.androidlauncher

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidlauncher.ui.theme.AndroidLauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLauncherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppList(context = this)
                }
            }
        }
    }
}

data class AppInfo(
    val label: String,
    val packageName: String,
    val icon: Drawable
)

@Composable
fun AppList(context: Context) {
    val intent = Intent(Intent.ACTION_MAIN, null).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
    }
    val packageManager = context.packageManager
    val apps = remember {
        val allApps = packageManager.queryIntentActivities(intent, 0)
        allApps.map { ri ->
            AppInfo(
                label = ri.loadLabel(packageManager).toString(),
                packageName = ri.activityInfo.packageName,
                icon = ri.activityInfo.loadIcon(packageManager)
            )
        }
    }

    // Display the list of apps

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        apps.forEach { app ->
            Button(onClick = {
                val launchIntent = Intent(Intent.ACTION_MAIN)
                launchIntent.setPackage(app.packageName)
                context.startActivity(launchIntent)
            }) {
                Text(text = app.label)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLauncherTheme {
        Greeting("Android")
    }
}