package com.yudhis.studyhive

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.yudhis.studyhive.databinding.ActivityForgotPasswordBinding
import java.util.*

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
        binding.txtBackToLogin.text = ss
        binding.txtBackToLogin.movementMethod = LinkMovementMethod.getInstance()

        binding.btnSendOtp.setOnClickListener{
            if (TextUtils.isEmpty(binding.fieldEmailForgotpass.text)) {
                binding.fieldEmailForgotpass.error = "Email harus diisi"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.fieldEmailForgotpass.text).matches()) {
                binding.fieldEmailForgotpass.error = "Email invalid"
            }
            else {
                //show otp input field
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.activity_reset_password_otp_popup)

                val textViewOtp = dialog.findViewById<TextView>(R.id.tv_kode_otp)
                val random = Random()

                val otp = StringBuilder()
                for (i in 0 until 4) { // mengulangi 4 kali untuk menghasilkan 4 digit kode OTP
                    otp.append(random.nextInt(10)) // menghasilkan digit acak dari 0 hingga 9
                }

                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    var i = 0
                    override fun run() {
                        if (i < otp.length) {
                            textViewOtp.text = otp.substring(0, i + 1)
                            i++
                            handler.postDelayed(this, 500) // menunda tampilan selama 500 milidetik
                        } else {
                            dialog.dismiss() // tutup dialog setelah 4 digit ditampilkan
                            val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
                            startActivity(intent) // pindah ke halaman ResetPasswordActivity
                        }
                    }
                }, 500)
                dialog.show()
                //
            }
        }
    }
}