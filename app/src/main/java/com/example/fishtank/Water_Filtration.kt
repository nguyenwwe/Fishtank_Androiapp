package com.example.fishtank

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.switchmaterial.SwitchMaterial

import android.content.Intent
import android.widget.Button

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class Water_Filtration : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    private var isUpdatingFromFirebase = false // Cờ chặn vòng lặp vô hạn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_filtration)

        val s3 = findViewById<SwitchMaterial>(R.id.switch3)
        val btnWaterFiltrationback= findViewById<Button>(R.id.button_water_filtration_back)

        // 2. Kết nối tới nhánh "switch1" trên Firebase
        ref = FirebaseDatabase.getInstance().reference.child("Home").child("Pump")

        // 3. LẮNG NGHE Firebase đổi dữ liệu -> Cập nhật lên Giao diện app
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isUpdatingFromFirebase = true // Bật cờ (Hệ thống đang tự đổi giao diện)

                // Lấy giá trị true/false từ Firebase về (Mặc định là false nếu chưa có dữ liệu)
                val isCheckedOnFirebase = snapshot.getValue(Boolean::class.java) ?: false
                s3.isChecked = isCheckedOnFirebase // Gạt nút trên màn hình theo Firebase

                isUpdatingFromFirebase = false // Hạ cờ (Hệ thống đã cập nhật xong)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Water_Filtration, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })



        // 4. Lắng nghe sự kiện click vào nút
        btnWaterFiltrationback.setOnClickListener {
            // Đóng màn hình hiện tại để tự động lùi về màn hình trước
            finish()
        }

        // 5. Bat Switch
        s3.setOnCheckedChangeListener { _, isChecked ->
            // Chỉ đẩy lên mạng nếu đây là do NGƯỜI DÙNG lấy tay gạt (không phải do mạng tự update)
            if (!isUpdatingFromFirebase) {
                ref.setValue(isChecked)
                    .addOnSuccessListener {
                        val status = if (isChecked) "BẬT" else "TẮT"
                        Toast.makeText(this, "Đã $status lọc thành công!", Toast.LENGTH_SHORT).show()
                    }
            }
        }






    }
}