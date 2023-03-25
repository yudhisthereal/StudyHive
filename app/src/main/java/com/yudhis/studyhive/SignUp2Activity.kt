package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView

class SignUp2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        val textViewSudahPunyaAkunLogin = "Sudah punya akun? Login"
        val buttonBuatAkun = findViewById<Button>(R.id.bt_buat_akun)
//        val textViewSudahPunyaAkunLogin = findViewById<TextView>(R.id.tv_sudah_punya_akun_login)
//
//        textViewSudahPunyaAkunLogin.setOnClickListener {
//            Intent(this, LoginActivity::class.java).also {
//                startActivity(it)
//            }
//        }
        val spannableString = SpannableString(textViewSudahPunyaAkunLogin)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignUp2Activity, LoginActivity::class.java))
            }
        }
        spannableString.setSpan(clickableSpan, 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = findViewById<TextView>(R.id.tv_sudah_punya_akun)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()

        buttonBuatAkun.setOnClickListener {  }
    }
}