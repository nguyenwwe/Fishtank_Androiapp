package com.example.fishtank

import android.os.Bundle
//import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.switchmaterial.SwitchMaterial

import android.content.Intent
import android.widget.Button

class Light : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        val s1 = findViewById<SwitchMaterial>(R.id.switch1)
        val s2 = findViewById<SwitchMaterial>(R.id.switch2)
        val btnlightback= findViewById<Button>(R.id.button_light_back)

        // 2. Lắng nghe sự kiện click vào nút
        btnlightback.setOnClickListener {
            // Đóng màn hình hiện tại để tự động lùi về màn hình trước
            finish()
        }

        ref = FirebaseDatabase.getInstance().reference.child("Home")


        s1.setOnCheckedChangeListener { _, v ->
            ref.child("Light/Light_1").setValue(if (v) 1 else 0)
        }
        s2.setOnCheckedChangeListener { _, v ->
            ref.child("Light/Light_2").setValue(if (v) 1 else 0)
        }




    }
}