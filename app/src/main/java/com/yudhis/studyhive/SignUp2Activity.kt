package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignUp2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        val buttonBuatAkun = findViewById<Button>(R.id.bt_buat_akun)
        val textViewSudahPunyaAkunLogin = findViewById<TextView>(R.id.tv_sudah_punya_akun_login)

        textViewSudahPunyaAkunLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        buttonBuatAkun.setOnClickListener {  }
    }
}