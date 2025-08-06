package com.example.fixpilot.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fixpilot.MainActivity
import com.example.fixpilot.R
import kotlinx.coroutines.delay
import com.example.fixpilot.ui.theme.FixPilotTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FixPilotTheme {
                SplashScreen {
                    // Nach Splash -> Main starten
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(true) {
        // Rotation animieren
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    LaunchedEffect(true) {
        // Text einblenden
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500)
        )
        // Gesamtzeit Splash
        delay(2500)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round_splash_screen), // dein Symbol
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .rotate(rotation.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "FixPilot",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.graphicsLayer { this.alpha = alpha.value }
            )
        }
    }
}
