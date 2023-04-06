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

        //membawa informasi data dari main activity ke course overview activity
        val tv1 = findViewById<TextView>(R.id.tv_1)
        val description = intent.getStringExtra("course_brief_description")
        tv1.text = description

        val tvJudulCourse = findViewById<TextView>(R.id.tv_judulcourse)
        val title = intent.getStringExtra("course_title")
        tvJudulCourse.text = title

        val tvRating = findViewById<TextView>(R.id.tv_2)
        val rating = intent.getStringExtra("course_rating")
        tvRating.text = rating.toString()

        val tvStartDate = findViewById<TextView>(R.id.tv_3)
        val startDate = intent.getStringExtra("course_startDate")
        tvStartDate.text = startDate

        val tvEndDate = findViewById<TextView>(R.id.tv_4)
        val endDate = intent.getStringExtra("course_endDate")
        tvEndDate.text = endDate

        //membuat fragmen detail, fragmen pembicara dan fragmen persyaratan
        val tvDetail = findViewById<TextView>(R.id.tv_detail)
        val tvPembicara = findViewById<TextView>(R.id.tv_pembicara)
        val tvPersyaratan = findViewById<TextView>(R.id.tv_persyaratan)

        //memanggil kelas fragmen detail, fragmen pembicara dan fragmen persyaratan
        val persyaratanFragment = PersyaratanFragment()
        val pembicaraFragment = PembicaraFragment()
        val detailFragment = detailFragment()

        val fullDescription = intent.getStringExtra("course_full_description")
        val courseContents = intent.getStringExtra("course_contents")

        val fragment = detailFragment().apply {
            arguments = Bundle().apply {
                putString("course_full_description", fullDescription)
                putString("course_contents", courseContents)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.flfragment, fragment)
            .addToBackStack(null)
            .commit()

        //TvDetail ditekan, frame layout menampilkan fragmen detail
        tvDetail.setOnClickListener {
            val fragment = detailFragment().apply {
                arguments = Bundle().apply {
                    putString("course_full_description", fullDescription)
                    putString("course_contents", courseContents)
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.flfragment, fragment)
                .addToBackStack(null)
                .commit()
        }
        //tvPembicara ditekan, frame layout menampilkan fragmen pembicara
        val pembicara1 = intent.getStringExtra("course_pembicara1")
        val pembicara2 = intent.getStringExtra("course_pembicara2")
        tvPembicara.setOnClickListener {
            val fragment = PembicaraFragment().apply {
                arguments = Bundle().apply {
                    putString("course_pembicara1", pembicara1)
                    putString("course_pembicara2", pembicara2)
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.flfragment, fragment)
                .addToBackStack(null)
                .commit()
        }
        //tvPersyaratan ditekan, frame layout menampilkan fragmen persyaratan
        tvPersyaratan.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragment, persyaratanFragment)
                commit()
            }
        }

        //mengatur tombol daftar agar ketika ditekan menuju signup course activity dan membawa data title, startdate dan enddate
        val btnDaftar = findViewById<Button>(R.id.bt_daftar)
        btnDaftar.setOnClickListener {
            Intent(this, SignUpCourseActivity::class.java).also {
                it.putExtra("course_title", title)
                it.putExtra("course_startDate", startDate)
                it.putExtra("course_endDate", endDate)
                startActivity(it)
            }
        }

        val btnBack = findViewById<Button>(R.id.bt_back)
        btnBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

    }

}