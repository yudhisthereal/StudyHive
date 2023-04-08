package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yudhis.studyhive.data.userData

class RegistrationTicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_ticket)

        //menampilkan data dari sign up course activity menuju ke regristration ticket activity
        val tvJudulCourse = findViewById<TextView>(R.id.tv_event)
        val title = intent.getStringExtra("course_title")
        tvJudulCourse.text = title

        val tvStartDate = findViewById<TextView>(R.id.tv_tanggal_mulai)
        val startDate = intent.getStringExtra("course_startDate")
        tvStartDate.text = startDate

        val tvEndDate = findViewById<TextView>(R.id.tv_tanggal_selesai)
        val endDate = intent.getStringExtra("course_endDate")
        tvEndDate.text = endDate

        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val displayName = user?.displayName
        val namaLengkap = findViewById<TextView>(R.id.tv_namapendaftar)
        namaLengkap.text = displayName

        //mengatur tombol seleseai agar ketika ditekan menuju ke main activity dan membawa data title, startdate dan enddate
        val btnSelesai = findViewById<Button>(R.id.bt_selesai)
        btnSelesai.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("course_title", title)
            intent.putExtra("course_startDate", startDate)
            intent.putExtra("course_endDate", endDate)
            startActivity(intent)
        }

        val checkedNicknames = intent.getStringArrayListExtra("checked_nicknames")
        val nicknamesTextView = findViewById<TextView>(R.id.partisipan1)
        nicknamesTextView.text = checkedNicknames?.joinToString("\n")

        }
    }
