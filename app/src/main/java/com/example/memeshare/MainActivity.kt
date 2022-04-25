package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    var currentImageUrl:String?=null
    private val url = "https://meme-api.herokuapp.com/gimme"
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.memeImageView)
        loadImage()
    }

    private fun loadImage() {
        //get data using API
        //Volley  -- server se data app me late he
        //Create request queue
        val queue = Volley.newRequestQueue(this)

//       //Request
        val request = JsonObjectRequest(Request.Method.GET, this.url, null,
            { response ->
                Log.d("Result", response.toString())
                Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loading).into(imageView)
            },

            {
                Log.e("error", it.toString())
                Toast.makeText(applicationContext, "Error in loading MeMe", Toast.LENGTH_LONG)
                    .show()

            })


//        add request queue to queue
        queue.add(request)

        // pic aso-- keep image view
    }

        fun nextMeme(view: View) {
            this.loadImage();
        }
    fun shareMeme(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type="image/jpg"
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey check this cool Meme $currentImageUrl")
        val chooser =Intent.createChooser(intent,"Share this Meme by")
        startActivity(chooser)
    }
}
