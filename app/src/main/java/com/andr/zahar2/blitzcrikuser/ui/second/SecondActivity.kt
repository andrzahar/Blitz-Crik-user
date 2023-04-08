package com.andr.zahar2.blitzcrikuser.ui.second

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.andr.zahar2.blitzcrikuser.R
import com.andr.zahar2.blitzcrikuser.ui.theme.BlitzCrikUserTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondActivity : ComponentActivity() {

    private fun hideSystemUI() {

        actionBar?.hide()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    private var mNotificationManager: NotificationManager? = null

    override fun onBackPressed() {
        super.onBackPressed()
        mNotificationManager?.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            ?: Toast.makeText(this, "ВЫКЛЮЧИ РЕЖИМ НЕ БЕСПОКОИТЬ", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        try {
            mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager!!.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
        } catch (e: Exception) {
            mNotificationManager = null
            Toast.makeText(this, "ВКЛЮЧИ РЕЖИМ НЕ БЕСПОКОИТЬ", Toast.LENGTH_SHORT).show()
        }

        setContent {

            val viewModel = hiltViewModel<SecondActivityViewModel>()
            val points = viewModel.statePoints.value
            val name = viewModel.name
            val showScreen = viewModel.stateShowScreen.value

            Content(
                points,
                name,
                showScreen
            )
        }

        hideSystemUI()
    }
}

@Composable
private fun Content(
    points: String,
    name: String,
    showScreen: Boolean
) {
    BlitzCrikUserTheme(darkTheme = true, dynamicColor = false) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {

            Image(
                painter = painterResource(
                    if (showScreen) R.drawable.splash_first else R.drawable.splash_second
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(260.dp))
                Text(
                    text = name.uppercase(),
                    color = Color.White,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(5.0f, 10.0f),
                            blurRadius = 75f
                        )
                    ),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.istok_web_bold)),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (!showScreen) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = points,
                    color = Color.White,
                    fontSize = 150.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(5.0f, 10.0f),
                            blurRadius = 75f
                        )
                    ),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.istok_web_bold)),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    Content(
        "6",
        "Анастасия",
        false
    )
}