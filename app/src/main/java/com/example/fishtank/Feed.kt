package com.example.fishtank

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Feed : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    private var isUpdatingFromFirebase = false // Cờ chặn vòng lặp vô hạn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        // Bien de doi background
        val bgImage = findViewById<ImageView>(R.id.bgImage);

        val s1 = findViewById<SwitchMaterial>(R.id.switch1)

        val btnFeedback= findViewById<Button>(R.id.button_feed_back)

        // 2. Kết nối tới nhánh "switch1" trên Firebase
        ref = FirebaseDatabase.getInstance().reference.child("Home").child("Feed")

        // 3. LẮNG NGHE Firebase đổi dữ liệu -> Cập nhật lên Giao diện app
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isUpdatingFromFirebase = true // Bật cờ (Hệ thống đang tự đổi giao diện)

                // Lấy giá trị true/false từ Firebase về (Mặc định là false nếu chưa có dữ liệu)
                val isCheckedOnFirebase = snapshot.getValue(Boolean::class.java) ?: false
                s1.isChecked = isCheckedOnFirebase // Gạt nút trên màn hình theo Firebase

                isUpdatingFromFirebase = false // Hạ cờ (Hệ thống đã cập nhật xong)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Feed, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // 4. Lắng nghe sự kiện click vào nút
        btnFeedback.setOnClickListener {
            // Đóng màn hình hiện tại để tự động lùi về màn hình trước
            finish()
        }
//        ref = FirebaseDatabase.getInstance().reference.child("Home")

        // 5. Bat Switch
        s1.setOnCheckedChangeListener { _, isChecked ->
            //5a. Chỉ đẩy lên mạng nếu đây là do NGƯỜI DÙNG lấy tay gạt (không phải do mạng tự update)
            if (!isUpdatingFromFirebase) {
                ref.setValue(isChecked)
                    .addOnSuccessListener {
                        val status = if (isChecked) "BẬT" else "TẮT"
                        Toast.makeText(this, "Đã $status cho ăn thành công!", Toast.LENGTH_SHORT).show()
                    }
            }
            //5b. Doi background
            if (isChecked) {
                bgImage.setImageResource(R.drawable.background_feeding);
            } else {
                bgImage.setImageResource(R.drawable.background_1);
            }
        }





    }
}