package com.example.memesharing

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var url :String?= null
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()

        val sharebtn:Button = findViewById(R.id.sharebtn)
        val nextbtn:Button = findViewById(R.id.nextbtn)

        sharebtn.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type= "text/plain"

            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this new meme $url")
            val chooser = Intent.createChooser(intent,"Share this meme:")
            startActivity(chooser)

        }

        nextbtn.setOnClickListener(){
            loadmeme()
        }








    }

    fun loadmeme(){
        val memeimg= findViewById<ImageView>(R.id.memeimage)
         url = "https://meme-api.com/gimme"
        val progressBar= findViewById<ProgressBar>(R.id.progress)
        progressBar.visibility=View.VISIBLE

        val queue=  Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
               val url = response.getString("url")

                Glide.with(this).load(url).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility= View.GONE
                        return false


                    }

                    override fun onResourceReady(

                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility= View.GONE
                        return false
                    }


                }).into(memeimg)
            },
            Response.ErrorListener{
                Toast.makeText(this,"Some error occured!!!",Toast.LENGTH_SHORT).show()
            })

        queue.add(jsonObjectRequest)




    }









}