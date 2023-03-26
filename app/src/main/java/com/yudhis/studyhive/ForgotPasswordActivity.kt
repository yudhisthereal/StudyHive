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
import android.view.View
import android.widget.TextView
import com.yudhis.studyhive.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ss = SpannableString("Kembali Ke Menu Masuk")
        val clickableSpan : ClickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {
                startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
            }
        }
        ss.setSpan(clickableSpan, 16, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ss.setSpan(StyleSpan(Typeface.BOLD), 16, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.txtBackToLogin.text = ss;
        binding.txtBackToLogin.movementMethod = LinkMovementMethod.getInstance()
    }
}