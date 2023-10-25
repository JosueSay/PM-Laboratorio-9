package edu.uvg.com.example.pruebaandroidfbsensores
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : ComponentActivity() {
    // Reference to your Firebase Database
    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen { usuario, correo, password ->
                saveEmployeeData(usuario, correo, password)
            }
        }
    }

    private fun saveEmployeeData(userUsuario: String, userCorreo: String, userPassword: String) {
        // Here, you'd perform your validation and data insertion as in your original code.
        // For example:
        if (userUsuario.isEmpty() || userCorreo.isEmpty() || userPassword.isEmpty()) {
            // Show error
            Toast.makeText(this, "Ingresa la información completa", Toast.LENGTH_LONG).show()
            return
        }

        val userId = dbRef.push().key!!

        val usuario = UserModel(userId, userUsuario, userCorreo, userPassword)

        dbRef.child(userId).setValue(usuario)
            .addOnCompleteListener {
                Toast.makeText(this, "Usuario ingresado", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onSave: (usuario: String, correo: String, password: String) -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Replace 'correo' function with a TextField for name
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") }
        )

        // Replace 'password' function with a TextField for age
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") }
        )

        // TextField for Salary
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )

        Button(onClick = { onSave(usuario, correo, password)}) {
            Text("Guardar datos")
        }
    }
}
