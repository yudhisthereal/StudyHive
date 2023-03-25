package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonSelanjutnya = findViewById<Button>(R.id.bt_selanjutnya)
        val textViewSudahPunyaAkunLogin = findViewById<TextView>(R.id.tv_sudah_punya_akun_login)

        buttonSelanjutnya.setOnClickListener {
            Intent(this, SignUp2Activity::class.java).also {
                startActivity(it)
            }
        }

        textViewSudahPunyaAkunLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}