package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.yudhis.studyhive.databinding.ActivityMainBinding

class CourseOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_overview)

        val tvDetail = findViewById<TextView>(R.id.tv_detail)
        val tvPembicara = findViewById<TextView>(R.id.tv_pembicara)
        val tvPersyaratan = findViewById<TextView>(R.id.tv_persyaratan)

        val persyaratanFragment = PersyaratanFragment()
        val pembicaraFragment = PembicaraFragment()
        val detailFragment = detailFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, detailFragment)
            commit()
        }
        tvDetail.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragment, detailFragment)
                commit()
            }
        }
        tvPembicara.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragment, pembicaraFragment)
                commit()
            }
        }
        tvPersyaratan.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragment, persyaratanFragment)
                commit()
            }
        }

        val btnDaftar = findViewById<Button>(R.id.bt_daftar)
        btnDaftar.setOnClickListener {
            Intent(this, SignUpCourseActivity::class.java).also {
                startActivity(it)
            }
        }

    }

}