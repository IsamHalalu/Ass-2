package com.example.analytics2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event.*
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_product.*

class addProduct : AppCompatActivity() {
    var URI = ""
    lateinit var FirebaseFirestore : FirebaseFirestore
    lateinit var FirebaseAnalytics : FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)



        FirebaseFirestore = Firebase.firestore
        FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(this)
        val b3 = Bundle()
        b3.putString(SCREEN_NAME,"add")
        b3.putString(SCREEN_CLASS,"addProduct")
        FirebaseAnalytics.logEvent(SCREEN_VIEW,bundle3)

      val c = intent.getIntExtra("c",0)

        buttonAddImage.setOnClickListener {

            val name = name.text.toString()


            val information = information.text.toString()

            val document = hashMapOf("name" to name,"information" to information,"image" to URI,"category" to c.toString())

            FirebaseFirestore.collection("analytics").add(document).addOnSuccessListener {_ ->
                    Toast.makeText(this,"تمت الاضافه بنجاح", Toast.LENGTH_SHORT).show() }.addOnFailureListener { _ ->
                    Toast.makeText(this,"يوجد خطأ", Toast.LENGTH_SHORT).show()
                }


            val bun = Bundle()
            bun.putString(ITEM_ID,"buttonAddImage")
            bun.putString(ITEM_NAME,"add of gallery")
            bun.putString(CONTENT_TYPE,"button")
            FirebaseAnalytics.logEvent(SELECT_CONTENT,bun)



            val i = Intent(this,Products::class.java)
            startActivity(i)
        }


       buttonGetImage.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3000)
                    return@setOnClickListener
                } else {
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 300)
                }



            } else {
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, 300)
            }
           val Bundle_b = Bundle()
           Bundle_b.putString(ITEM_ID,"GetImage")
           Bundle_b.putString(ITEM_NAME,"get")
           Bundle_b.putString(CONTENT_TYPE,"button")
           FirebaseAnalytics.logEvent(SELECT_CONTENT,Bundle_b)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 300) {
            URI = data!!.data.toString()
            image.setImageURI(Uri.parse(URI))
        }
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            3000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 300)
                } else {
                  finish()
                }
                return
            }
        }
    }




}