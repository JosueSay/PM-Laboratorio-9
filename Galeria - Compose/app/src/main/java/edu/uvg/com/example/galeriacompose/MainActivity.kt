package edu.uvg.com.example.galeriacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uvg.com.example.galeriacompose.ui.theme.ColoresApp
import edu.uvg.com.example.galeriacompose.ui.theme.FormasApp
import edu.uvg.com.example.galeriacompose.ui.theme.FuentesApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            miPerfil_pantalla()
        }
    }
}


@Composable
fun miPerfil_pantalla() {

    var nombreUsuario = "Josué Say"
    var correoUsuario = "say22801@correo.com"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColoresApp.colorFondo)
    ) {

        tituloPerfil()
        fotoPerfil()
        cambiarImagen()
        //Spacer(modifier = Modifier.height(90.dp)) // Espacio antes de la nueva sección
        //Spacer(modifier = Modifier.weight(1f))  // Espaciador que ocupa todo el espacio restante
        infoUsuario(nombreUsuario = nombreUsuario, correoUsuario = correoUsuario)
    }


}

// TITULO APP
@Composable
fun tituloPerfil() {

    val gradient = Brush.verticalGradient(
        0.0f to ColoresApp.degradado_inicio,
        1.0f to ColoresApp.colorFondo
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = gradient)
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Perfil",
            style = FuentesApp.tituloScreen
        )
    }
}

// FOTO PERFIL
@Composable
fun fotoPerfil() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColoresApp.colorFondo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_perfil2),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(ColoresApp.colorPerfil)
        )
    }
}

// CAMBIAR IMAGEN
@Composable
fun cambiarImagen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColoresApp.colorFondo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = { /*ACCION*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camara1),
                    contentDescription = "Icono de cámara",
                    tint = ColoresApp.colorFuerte,
                    modifier = Modifier
                        .size(35.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Acción de editar perfil */ },
                colors = ButtonDefaults.buttonColors(ColoresApp.colorFuerte)
            ) {
                Text(
                    text = "Editar",
                    style = FuentesApp.regular,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}

// INFO USUARIO
@Composable
fun infoUsuario(nombreUsuario: String, correoUsuario: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ColoresApp.colorFondo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Línea gris
        Divider(color = androidx.compose.ui.graphics.Color.Gray, thickness = 1.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Alineación vertical en el centro
        ) {
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Nombre:", style = FuentesApp.negrita)
                Spacer(modifier = Modifier.height(34.dp))
                Text(text = "Correo:", style = FuentesApp.negrita)
            }
            Column(
                modifier = Modifier.weight(2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = nombreUsuario,
                    style = FuentesApp.regular
                )
                Spacer(modifier = Modifier.height(43.dp)) // Espacio ajustable
                Text(text = correoUsuario, style = FuentesApp.regular)
            }
        }

        // Línea gris
        Divider(color = androidx.compose.ui.graphics.Color.Gray, thickness = 1.dp)

        // "Guardar Cambios"
        Spacer(modifier = Modifier.height(90.dp)) // Espacio antes de la nueva sección
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(ColoresApp.colorFuerte),
            shape = FormasApp.bordes
        ) {
            Text(
                text = "Guardar Cambios",
                style = FuentesApp.regular,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = ColoresApp.colorFondo
            )
        }
    }
}


@Preview
@Composable
fun perfilPreview() {
    miPerfil_pantalla()
}