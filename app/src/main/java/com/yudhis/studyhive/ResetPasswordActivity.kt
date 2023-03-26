package com.yudhis.studyhive

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.yudhis.studyhive.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fieldConfirmNewpass.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                //check for match
                val newPass = binding.fieldNewpass.text.toString()
                val confirmNewPass = binding.fieldConfirmNewpass.text.toString()
                if (newPass != confirmNewPass) {
                    binding.fieldConfirmNewpass.error = "Password tidak cocok"
                    binding.button.isEnabled = false
                }
                else {
                    //reset password
                    binding.fieldConfirmNewpass.error = null
                    binding.button.isEnabled = true

                    }

                }
            }
        val btnResetPass = findViewById<Button>(R.id.button)
        btnResetPass.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_reset_password_success_popup)

            val btnKembaliLogin = dialog.findViewById<Button>(R.id.bt_kembali_login)
            btnKembaliLogin.setOnClickListener {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
            dialog.show()
        }

    }
}