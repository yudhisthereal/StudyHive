package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUpCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_course)

        val tvJudulCourse = findViewById<TextView>(R.id.tv_judulcourse)
        val title = intent.getStringExtra("course_title")
        tvJudulCourse.text = title

        val tvStartDate = findViewById<TextView>(R.id.tv_startDate)
        val startDate = intent.getStringExtra("course_startDate")
        tvStartDate.text = startDate

        val tvEndDate = findViewById<TextView>(R.id.tv_endDate)
        val endDate = intent.getStringExtra("course_endDate")
        tvEndDate.text = endDate

        val btnBuatTiket = findViewById<Button>(R.id.bt_buat_tiket)
        btnBuatTiket.setOnClickListener {
            Intent(this, RegistrationTicketActivity::class.java).also {
                it.putExtra("course_title", title)
                it.putExtra("course_startDate", startDate)
                it.putExtra("course_endDate", endDate)
                startActivity(it)
            }
        }
    }


}