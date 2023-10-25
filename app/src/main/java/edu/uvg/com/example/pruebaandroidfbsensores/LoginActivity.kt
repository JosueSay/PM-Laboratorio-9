package edu.uvg.com.example.pruebaandroidfbsensores
import android.content.Intent
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
import com.google.firebase.database.*

class LoginActivity : ComponentActivity() {

    // Reference to your Firebase Database
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")

        setContent {
            LoginScreen { correo, password ->
                loginUser(correo, password)
            }
        }
    }

    private fun loginUser(correo: String, password: String) {
        if (correo.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Ingresa los datos completos", Toast.LENGTH_LONG).show()
            return
        }

        // Show a loading message (you might want to replace this with a real loading indicator in your Composable UI)
        Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show()

        // Query Firebase for a user with the matching email
        val query: Query = dbRef.orderByChild("correo").equalTo(correo)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // If the user exists, check the password
                    for (userSnapshot in snapshot.children) {
                        val fetchedUser = userSnapshot.getValue(UserModel::class.java)
                        if (fetchedUser != null) {
                            if (fetchedUser.password == password) {
                                // Password is correct, navigate to the next activity
                                startActivity(Intent(this@LoginActivity, IngresoExitoso::class.java))
                                finish()
                                return
                            }
                        }
                    }
                    // If we haven't returned yet, the password was wrong
                    Toast.makeText(this@LoginActivity, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                } else {
                    // User does not exist
                    Toast.makeText(this@LoginActivity, "No existe usuario con el correo ", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting data failed, log a message
                Toast.makeText(this@LoginActivity, "Error de ingreso: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (correo: String, password: String) -> Unit) {
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
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") }
        )

        Button(onClick = { onLogin(correo, password) }) {
            Text("Iniciar sesión")
        }
    }
}
