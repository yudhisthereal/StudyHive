package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AccountInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        val tvKembali = findViewById<TextView>(R.id.tv_kembali)
        val btnUbahPassword = findViewById<Button>(R.id.bt_ubah_password)

        tvKembali.setOnClickListener {
            Intent(this, LandingPageActivity::class.java).also {
                startActivity(it)
            }
        }

        btnUbahPassword.setOnClickListener {
            Intent(this,ChangePasswordAccountInfoActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}