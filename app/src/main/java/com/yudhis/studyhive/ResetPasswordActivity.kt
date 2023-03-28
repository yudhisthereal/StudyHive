package com.yudhis.studyhive

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yudhis.studyhive.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {
    private val db = Firebase.firestore
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
                }
            }
        }
        binding.btnResetPass.setOnClickListener {
            val newPass = binding.fieldNewpass.text.toString()
            val confirmPass = binding.fieldConfirmNewpass.text.toString()
            if (newPass.isBlank()){
                binding.fieldNewpass.error = "Wajib diisi"
                return@setOnClickListener
            }
            else if (confirmPass.isBlank()) {
                binding.fieldConfirmNewpass.error = "Wajib diisi"
                return@setOnClickListener
            }
            else if (confirmPass != newPass) {
                val toast = Toast.makeText(this, "Password harus cocok", 3.toInt())
                toast.show()
                return@setOnClickListener
            }
            //reset password
            resetPassword(newPass = binding.fieldNewpass.text.toString())
            //redirect to login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun resetPassword(email : String? = null, newPass : String) {
        val toast = Toast.makeText(this, "Password Direset! (TPI BOONG)", 1.toInt())
        toast.show()
    }
}