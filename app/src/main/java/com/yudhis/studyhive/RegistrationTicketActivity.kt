package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrationTicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_ticket)

        //menampilkan data dari sign up course activity menuju ke regristration ticket activity
        val tvJudulCourse = findViewById<TextView>(R.id.tv_event)
        val title = intent.getStringExtra("course_title")
        tvJudulCourse.text = title

        val tvStartDate = findViewById<TextView>(R.id.tv_tanggalmulai)
        val startDate = intent.getStringExtra("course_startDate")
        tvStartDate.text = startDate

        val tvEndDate = findViewById<TextView>(R.id.tv_tanggalselesai)
        val endDate = intent.getStringExtra("course_endDate")
        tvEndDate.text = endDate

        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val displayName = user?.displayName
        val namaLengkap = findViewById<TextView>(R.id.tv_namapendaftar)
        namaLengkap.text = displayName

        //mengatur tombol seleseai agar ketika ditekan menuju ke main activity dan membawa data title, startdate dan enddate
        val btnSelesai = findViewById<Button>(R.id.bt_selesai)
        btnSelesai.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                it.putExtra("course_title", title)
                it.putExtra("course_startDate", startDate)
                it.putExtra("course_endDate", endDate)
                startActivity(it)
            }
        }
    }
}