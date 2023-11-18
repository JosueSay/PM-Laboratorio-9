package edu.uvg.com.example.galeriacompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uvg.com.example.galeriacompose.R

// Creación de fuentes personalizadas
//***** 1. crear un Android Resource Directory en res de tipo "font"
//***** 2. añadir las fuentes .ttf sin mayúsculas y sin guiones al directorio font
//***** 3. crear un FontFamily para llamar a las fuentes
//***** 4. definir variables específicas para los tipos de texto
//***** 5. se añade un "style = nombre_fuente" para añadir la fuente a un Text

// Tipografías personalizadas
val tipografiasApp = FontFamily(
    // Font = clase para representar la tipografía
    // R.font es la referencia hacía una recurso, en este caso una fuente tipografica
    //FontWeiht es el peso de la fuente (grosor)
    Font(R.font.quicksand_bold, FontWeight.Bold),
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_light, FontWeight.Light),
    Font(R.font.quicksand_medium, FontWeight.Medium),
    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
)

// Tipografías específicas
object FuentesApp{
    val negrita = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    val regular = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    val light = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    )
    val medio = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
    val snegrita = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )

    val tituloScreen = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.SemiBold,
        fontSize = 50.sp
    )

    val tituloScreenGrande = TextStyle(
        fontFamily = tipografiasApp,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp
    )


}

@Composable
@Preview
fun TypographyPreview() {
    Surface {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Texto en Negrita", style = FuentesApp.negrita)
            Text("Texto Regular", style = FuentesApp.regular)
            Text("Texto Ligero", style = FuentesApp.light)
            Text("Texto Medio", style = FuentesApp.medio)
            Text("Texto Semi-Negrita", style = FuentesApp.snegrita)
            Text("Título de la Pantalla", style = FuentesApp.tituloScreen)
            Text("Título de la Pantalla", style = FuentesApp.tituloScreenGrande)
        }
    }
}