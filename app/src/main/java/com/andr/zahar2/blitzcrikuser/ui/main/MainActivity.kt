package com.andr.zahar2.blitzcrikuser.ui.main

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andr.zahar2.blitzcrikuser.ui.leading.LeadingActivity
import com.andr.zahar2.blitzcrikuser.ui.second.SecondActivity
import com.andr.zahar2.blitzcrikuser.ui.theme.BlitzCrikUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val mNotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!mNotificationManager.isNotificationPolicyAccessGranted) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(intent)
            }
        } catch (e: Exception) { }

        setContent {

            val viewModel = hiltViewModel<MainActivityViewModel>()

            var name by remember { mutableStateOf("") }
            var host by remember { mutableStateOf(viewModel.host) }
            var port by remember { mutableStateOf(viewModel.port.toString()) }

            Content(
                name = name,
                nameChange = { name = it },
                host = host,
                port = port,
                hostChange = { host = it },
                portChange = { port = it },
                onClickUser = {
                    viewModel.onButtonClick(name, host, port) {
                        startActivity(Intent(this, SecondActivity::class.java))
                    }
                }
            ) {
                viewModel.onButtonClick(name, host, port) {
                    startActivity(Intent(this, LeadingActivity::class.java))
                }
            }
        }
    }
}

@Composable
private fun Content(
    name: String,
    nameChange: (String) -> Unit,
    host: String,
    port: String,
    hostChange: (String) -> Unit,
    portChange: (String) -> Unit,
    onClickUser: () -> Unit,
    onClickLeading: () -> Unit
) {
    BlitzCrikUserTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = name,
                    onValueChange = nameChange,
                    label = { Text("Имя") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusable()
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    TextField(
                        value = host,
                        onValueChange = hostChange,
                        label = { Text("Хост") },
                        modifier = Modifier
                            .width(200.dp)
                            .focusable()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        value = port,
                        onValueChange = portChange,
                        label = { Text("Порт") },
                        modifier = Modifier.focusable()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onClickUser,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Игрок")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onClickLeading,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Ведущий",
                        fontSize = 30.sp
                    )
                }
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
    BlitzCrikUserTheme {
        Content(
            name = "test",
            nameChange = { },
            host = "192.168.10.89",
            port = "2207",
            hostChange = { },
            portChange = { },
            onClickUser = { }
        ) {

        }
    }
}