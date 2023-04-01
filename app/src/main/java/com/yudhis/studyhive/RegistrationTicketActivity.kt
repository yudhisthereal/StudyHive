package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegistrationTicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_ticket)

        val btnSelesai = findViewById<Button>(R.id.bt_selesai)
        btnSelesai.setOnClickListener {
            Intent(this, CourseOverviewActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}