package edu.uvg.com.example.pruebaandroidfbsensores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class IngresoExitoso : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Text("Hola, mundo")
        }
    }
}
