package com.example.analytics2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.FirebaseFirestoreKtxRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_products.*

class Products : AppCompatActivity() {
    lateinit var FirebaseAnalytics : FirebaseAnalytics
    lateinit var FirebaseFirestore : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        FirebaseFirestore = Firebase.firestore
        FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_NAME,"products")
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.SCREEN_CLASS,"products")
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SCREEN_VIEW,bundle)
        val c = intent.getIntExtra("category",0)
        val    data = mutableListOf<item>()
        FirebaseFirestore.collection("analytics").whereEqualTo("category",c.toString()).get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot) {
                val name = doc.getString("name")
                val information = doc.getString("information")
                val image = doc.getString("image")

                if (name != null&& information != null && image != null){
                    data.add(item(doc.id,name,information,image))
                }
            }
            val adapter = adapter(this, data)
            recyclerView101.adapter = adapter
            recyclerView101.layoutManager = LinearLayoutManager(this)
        }
        buttnAdd.setOnClickListener {
            val i = Intent(this,addProduct::class.java)
            i.putExtra("c",c)
            startActivity(i)
        }
    }
}