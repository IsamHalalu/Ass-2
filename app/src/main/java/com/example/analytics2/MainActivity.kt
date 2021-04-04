package com.example.analytics2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event.*
import com.google.firebase.analytics.FirebaseAnalytics.Param.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var FirebaseAnalytics : FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(this)
        val bundle1 = Bundle()
        bundle1.putString(SCREEN_NAME,"MainActivity")
        bundle1.putString(SCREEN_CLASS,"MainActivity")
        FirebaseAnalytics.logEvent(SCREEN_VIEW,bundle1)
        val i = Intent(this,Products::class.java)
        food.setOnClickListener {
            i.putExtra("category",1)
            val bundle = Bundle()
            bundle.putString(ITEM_ID,"food")
            bundle.putString(ITEM_NAME,"food button")
            bundle.putString(CONTENT_TYPE,"button")
            FirebaseAnalytics.logEvent(SELECT_CONTENT,bundle)
            startActivity(i)
        }
        clothes.setOnClickListener {
            i.putExtra("category",2)
            val bundle = Bundle()
            bundle.putString(ITEM_ID,"clothes")
            bundle.putString(ITEM_NAME,"clothes button")
            bundle.putString(CONTENT_TYPE,"button")
            FirebaseAnalytics.logEvent(SELECT_CONTENT,bundle)
            startActivity(i)
        }

        electronic.setOnClickListener {
            i.putExtra("category",3)
            val bundle = Bundle()
            bundle.putString(ITEM_ID,"electronic")
            bundle.putString(ITEM_NAME,"electronic button")
            bundle.putString(CONTENT_TYPE,"button")
            FirebaseAnalytics.logEvent(SELECT_CONTENT,bundle)
            startActivity(i)
        }
    }
}