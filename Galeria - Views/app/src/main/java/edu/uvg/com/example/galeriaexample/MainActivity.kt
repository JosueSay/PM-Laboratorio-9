package edu.uvg.com.example.galeriaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*

class MainActivity : AppCompatActivity() {


    val pickMedia = registerForActivityResult(PickVisualMedia()){
        uri->
        if(uri!= null){
            imageSeleccion.setImageURI(uri)
        }else {

            // Sin imagen
        }
    }

    lateinit var btnImage:Button
    lateinit var imageSeleccion:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnImage = findViewById(R.id.btnImage)
        imageSeleccion = findViewById(R.id.imageSeleccion)
        btnImage.setOnClickListener{

            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))




        }




    }


}