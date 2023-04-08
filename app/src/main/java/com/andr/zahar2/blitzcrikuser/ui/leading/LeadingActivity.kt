package com.andr.zahar2.blitzcrikuser.ui.leading

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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.andr.zahar2.blitzcrikuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadingActivity : ComponentActivity() {

    private fun hideSystemUI() {

        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars

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

            val viewModel = hiltViewModel<LeadingActivityViewModel>()
            val round = viewModel.stateRound.value

            if (round.isEmpty()) BeforeStart() else Content(round = round)
        }

        hideSystemUI()
    }
}

@Composable
private fun BeforeStart() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {}
}

@Composable
private fun Content(
    round: String
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(46, 105, 104)
    ) {
        Text(
            text = round.uppercase(),
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
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    Content(
        "Раунд 1\nФакты"
    )
}