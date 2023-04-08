package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.yudhis.studyhive.data.userData

class SignUpCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_course)
        val checkboxes = listOf<CheckBox>(
            findViewById<CheckBox>(R.id.partisipan1),
            findViewById<CheckBox>(R.id.partisipan2),
            findViewById<CheckBox>(R.id.partisipan3),
            findViewById<CheckBox>(R.id.partisipan4),
            findViewById<CheckBox>(R.id.partisipan5)
        )
        val participantIds = mutableListOf<String>()

        for((i, participant) in userData.participants.values.withIndex()) {
            checkboxes[i].text = participant.pNickName
            checkboxes[i].isEnabled = true
            participantIds.add(i, participant.pId)
        }

        //menampilkan data dari course overview activity menuju ke sign up course activity
        val tvJudulCourse = findViewById<TextView>(R.id.tv_judulcourse)
        val title = intent.getStringExtra("course_title")
        tvJudulCourse.text = title

        val tvStartDate = findViewById<TextView>(R.id.tv_startDate)
        val startDate = intent.getStringExtra("course_startDate")
        tvStartDate.text = startDate

        val tvEndDate = findViewById<TextView>(R.id.tv_endDate)
        val endDate = intent.getStringExtra("course_endDate")
        tvEndDate.text = endDate




        //mengatur tombol buat tiket agar ketika ditekan menuju ke regristtration ticket activty dan membawa data title,startdate dan enddate
        val btnBuatTiket = findViewById<Button>(R.id.bt_buat_tiket)
        btnBuatTiket.setOnClickListener {
            val intent = Intent(this, RegistrationTicketActivity::class.java)
            val checkedNicknames = mutableListOf<String>()

            for (checkbox in checkboxes) {
                if (checkbox.isChecked) {
                    checkedNicknames.add(checkbox.text.toString())
                }
            }
                intent.putExtra("course_title", title)
                intent.putExtra("course_startDate", startDate)
                intent.putExtra("course_endDate", endDate)
                intent.putStringArrayListExtra("checked_nicknames", ArrayList(checkedNicknames))
                startActivity(intent)
            }
        }
    }