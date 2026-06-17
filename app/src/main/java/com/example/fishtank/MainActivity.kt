package com.example.fishtank

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.switchmaterial.SwitchMaterial

import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 1. Ánh xạ nút bấm từ giao diện XML
        val btn_Feed= findViewById<Button>(R.id.btn_Feed)
        val btn_Light= findViewById<Button>(R.id.btn_Light)
        val btn_Oxy= findViewById<Button>(R.id.btn_Oxy)
        val btn_Water_Filtration= findViewById<Button>(R.id.btn_Water_Filtration)


        // 2. Lắng nghe sự kiện click vào nút
        btn_Feed.setOnClickListener {
            // Tạo một Intent để đi từ MainActivity sang Feed
            val intent = Intent(this, Feed::class.java)

            // Kích hoạt lệnh chuyển trang
            startActivity(intent)
        }
        btn_Light.setOnClickListener {
            // Tạo một Intent để đi từ MainActivity sang Light
            val intent = Intent(this, Light::class.java)

            // Kích hoạt lệnh chuyển trang
            startActivity(intent)
        }
        btn_Oxy.setOnClickListener {
            // Tạo một Intent để đi từ MainActivity sang Oxy
            val intent = Intent(this, Oxy::class.java)

            // Kích hoạt lệnh chuyển trang
            startActivity(intent)
        }
        btn_Water_Filtration.setOnClickListener {
            // Tạo một Intent để đi từ MainActivity sang Water_Filtration
            val intent = Intent(this, Water_Filtration::class.java)

            // Kích hoạt lệnh chuyển trang
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().reference.child("Home")

//        s1.setOnCheckedChangeListener { _, v ->
//            ref.child("Feed").setValue(if (v) 1 else 0)
//        }
//
//        s2.setOnCheckedChangeListener { _, v ->
//            ref.child("Light").setValue(if (v) 1 else 0)
//        }
//
//        s3.setOnCheckedChangeListener { _, v ->
//            ref.child("Pump").setValue(if (v) 1 else 0)
//        }
    }
}