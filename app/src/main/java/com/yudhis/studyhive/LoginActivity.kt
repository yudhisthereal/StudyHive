package com.yudhis.studyhive

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val txtSignup = findViewById<TextView>(R.id.txt_signup)
        val ss = SpannableString("Belum punya akun? Daftar")
        val clickableSignup : ClickableSpan = object : ClickableSpan(){
            override fun onClick(p0 : View) {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
        }
        ss.setSpan(clickableSignup, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        txtSignup.text = ss
        txtSignup.movementMethod = LinkMovementMethod.getInstance()
    }
}