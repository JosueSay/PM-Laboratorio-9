package edu.uvg.com.example.galeriacompose

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.io.IOException
import java.util.concurrent.Executor

class LogicCam : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraHomeScreen()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraHomeScreen() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context) }
    val lifecycle = LocalLifecycleOwner.current

    // Estado para almacenar la URI de la imagen
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }


    // Estado para seguir qué cámara está seleccionada
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }

    Scaffold(
        // ... [tu código existente para Scaffold]
        floatingActionButton = {
            // Usa Box para posicionar IconButton en la parte inferior derecha
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),  // ajusta el padding si es necesario
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(
                    onClick = {
                        val executor = ContextCompat.getMainExecutor(context)
                        tomarFoto(cameraController, executor, context) { uri ->
                            imagenUri = uri
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_tomar_foto2),
                        contentDescription = "Tomar foto",
                        tint = Color.White // el color de tu icono
                    )
                }
            }
        },


        ) {
        if (permissionState.status.isGranted) {
            Text(text = "Permiso Concedido", modifier = Modifier.padding(it))
            VistaCamaraCompose(cameraController, lifecycle, modifier = Modifier.padding(it))


            }
            // Contenedor para los botones
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Ajusta el padding si es necesario
                contentAlignment = Alignment.BottomCenter // Alinea los elementos en la parte inferior central
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, // Espacia los elementos uniformemente
                    modifier = Modifier.fillMaxWidth() // Hace que el Row llene el ancho de la pantalla
                ) {
                    IconButton(
                        onClick = {
                            // Acción para el primer botón (cambiar cámara)
                            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                            cameraController.cameraSelector = cameraSelector
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_reverse_camera),
                            contentDescription = "Cambiar Cámara",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Esto empujará el segundo IconButton hacia la derecha

                    IconButton(
                        onClick = {
                            // Acción para el segundo botón (tomar foto)
                            val executor = ContextCompat.getMainExecutor(context)
                            tomarFoto(cameraController, executor, context) { uri ->
                                imagenUri = uri
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_galeria),
                            contentDescription = "Tomar foto",
                            tint = Color.White
                        )
                    }
                }
            }
        } else {
            Text(text = "Permiso NO Concedido", modifier = Modifier.padding(it))
        }
    }
}

// Función para cargar un bitmap desde una URI
fun loadBitmapFromUri(uri: Uri, context: Context): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: IOException) {
        // Manejar excepción
        null
    }
}

private fun tomarFoto(
    cameraController: LifecycleCameraController,
    executor: Executor,
    context: Context,  // Agregamos un parámetro de tipo Context
    onFotoTomada: (Uri) -> Unit
) {
    val file = File.createTempFile("imagentest", ".jpg")
    val outputDirectory = ImageCapture.OutputFileOptions.Builder(file).build()

    cameraController.takePicture(
        outputDirectory,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let {
                    onFotoTomada(it)

                    try {
                        val contentValues = ContentValues().apply {
                            put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
                            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                        }

                        val contentResolver = context.contentResolver
                        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                        uri?.let {
                            val outputStream = contentResolver.openOutputStream(it)
                            outputStream?.use { stream ->
                                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            }
                        }
                    } catch (e: Exception) {
                        println("Error al guardar la imagen en la galería: ${e.message}")
                    }
                }
            }

            override fun onError(exception: ImageCaptureException) {
                println("Error al capturar imagen: ${exception.message}")
            }
        })
}


@Composable
fun VistaCamaraCompose(
    cameraController: LifecycleCameraController,
    lifecycle: LifecycleOwner,
    modifier: Modifier
) {
    cameraController.bindToLifecycle(lifecycle)

    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }

        previewView.controller = cameraController
        previewView
    })
}
