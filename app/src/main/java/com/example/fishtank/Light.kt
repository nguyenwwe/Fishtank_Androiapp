package com.example.fishtank

import android.os.Bundle
//import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.switchmaterial.SwitchMaterial

import android.content.Intent
import android.widget.Button
import android.widget.ImageView

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class Light : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    private var isUpdatingFromFirebase = false // Cờ chặn vòng lặp vô hạn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        // Bien de doi background
        val bgImage = findViewById<ImageView>(R.id.bgImage);
        var currentBackground = R.drawable.background_1;


        val s1 = findViewById<SwitchMaterial>(R.id.switch1)
        val s2 = findViewById<SwitchMaterial>(R.id.switch2)
        val btnlightback= findViewById<Button>(R.id.button_light_back)

        // 2. Kết nối tới nhánh "switch1" trên Firebase
        ref = FirebaseDatabase.getInstance().reference.child("Home").child("Light")


        // 3. LẮNG NGHE Firebase đổi dữ liệu -> Cập nhật lên Giao diện app
        ref.child("Light_1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isUpdatingFromFirebase = true // Bật cờ (Hệ thống đang tự đổi giao diện)

                // Lấy giá trị true/false từ Firebase về (Mặc định là false nếu chưa có dữ liệu)
                val isCheckedOnFirebase = snapshot.getValue(Boolean::class.java) ?: false
                s1.isChecked = isCheckedOnFirebase // Gạt nút trên màn hình theo Firebase

                isUpdatingFromFirebase = false // Hạ cờ (Hệ thống đã cập nhật xong)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Light, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


        // 4. Bật Switch
        s1.setOnCheckedChangeListener { _, isChecked ->
            //4a. Chỉ đẩy lên mạng nếu đây là do NGƯỜI DÙNG lấy tay gạt (không phải do mạng tự update)
            if (!isUpdatingFromFirebase) {
                ref.child("Light_1").setValue(isChecked)
                    .addOnSuccessListener {
                        val status = if (isChecked) "BẬT" else "TẮT"
                        Toast.makeText(this, "Đã $status đèn vàng thành công!", Toast.LENGTH_SHORT).show()
                    }
            }
            //4b. Doi background
            if (isChecked) {
                if( currentBackground == R.drawable.background_1){
                    bgImage.setImageResource(R.drawable.background_ly);
                    currentBackground = R.drawable.background_ly;
                }else{
                    bgImage.setImageResource(R.drawable.background_lb);
                    currentBackground = R.drawable.background_lb;
                }
            } else {
            if( currentBackground == R.drawable.background_lb){
                bgImage.setImageResource(R.drawable.background_lw);
                currentBackground = R.drawable.background_lw;

            }else{
                bgImage.setImageResource(R.drawable.background_1);
                currentBackground = R.drawable.background_1;


            }
        }
        }
        // 5. Lắng nghe sự kiện click vào nút
        btnlightback.setOnClickListener {
            // Đóng màn hình hiện tại để tự động lùi về màn hình trước
            finish()
        }


        // 6. LẮNG NGHE Firebase đổi dữ liệu -> Cập nhật lên Giao diện app
        ref.child("Light_2").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isUpdatingFromFirebase = true // Bật cờ (Hệ thống đang tự đổi giao diện)

                // Lấy giá trị true/false từ Firebase về (Mặc định là false nếu chưa có dữ liệu)
                val isCheckedOnFirebase = snapshot.getValue(Boolean::class.java) ?: false
                s2.isChecked = isCheckedOnFirebase // Gạt nút trên màn hình theo Firebase

                isUpdatingFromFirebase = false // Hạ cờ (Hệ thống đã cập nhật xong)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Light, "Lỗi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })


        // 7.Bat Switch
        s2.setOnCheckedChangeListener { _, isChecked ->
            //7a. Chỉ đẩy lên mạng nếu đây là do NGƯỜI DÙNG lấy tay gạt (không phải do mạng tự update)
            if (!isUpdatingFromFirebase) {
                ref.child("Light_2").setValue(isChecked)
                    .addOnSuccessListener {
                        val status = if (isChecked) "BẬT" else "TẮT"
                        Toast.makeText(this, "Đã $status đèn trắng thành công!", Toast.LENGTH_SHORT).show()
                    }
            }
            //7b. Doi hinh nen
            if (isChecked) {
                if( currentBackground == R.drawable.background_1){
                    bgImage.setImageResource(R.drawable.background_lw);
                    currentBackground = R.drawable.background_lw;
                }else{
                    bgImage.setImageResource(R.drawable.background_lb);
                    currentBackground = R.drawable.background_lb;
                }
            } else {
                if( currentBackground == R.drawable.background_lb){
                    bgImage.setImageResource(R.drawable.background_ly);
                    currentBackground = R.drawable.background_ly;

                }else{
                    bgImage.setImageResource(R.drawable.background_1);
                    currentBackground = R.drawable.background_1;


                }
            }

        }

    }
}