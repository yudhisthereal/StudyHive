package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_course)

        val btnBuatTiket = findViewById<Button>(R.id.bt_buat_tiket)
        btnBuatTiket.setOnClickListener {
            Intent(this, RegistrationTicketActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}