package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.widget.TextView
import com.yudhis.studyhive.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.fieldConfirmNewpass.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus){
                //check for match
                val newPass = binding.fieldNewpass.text
                val confirmNewPass = binding.fieldConfirmNewpass.text
                if (newPass != confirmNewPass) {
                    binding.fieldConfirmNewpass.error = "Password tidak cocok"
                }
                else {
                    //reset password

                    //redirect to login
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}