package edu.uvg.com.example.pruebaandroidfbsensores

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    // Cambios de activities
    val lanzador = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
    }
    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Aquí, `this` se refiere al contexto de la Composable function.
            // Necesitamos referirnos al contexto de la Activity, así que usamos `ContextAmbient.current`.
            val navegacion = Intent(contexto, LoginActivity::class.java)
            lanzador.launch(navegacion)
        }) {
            Text("Ingresar")
        }

        // Espaciado entre botones
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val navegacion = Intent(contexto, RegisterActivity::class.java)
            lanzador.launch(navegacion)
        }) {
            Text("Registrar")
        }

        // Espaciado entre botones
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val navegacion = Intent(contexto, HomeScreen::class.java)
            lanzador.launch(navegacion)
        }) {
            Text("USAR CAMARA")
        }

    }
}


