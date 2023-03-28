package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ChangePasswordAccountInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password_account_info)

        val btnKembali = findViewById<TextView>(R.id.tv_kembali)

        btnKembali.setOnClickListener {
            Intent(this, AccountInfoActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}