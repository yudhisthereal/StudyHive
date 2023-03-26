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
import android.widget.Toast
import com.yudhis.studyhive.LoginActivity.Companion.ACCOUNT_INFO
import com.yudhis.studyhive.databinding.ActivityResetPasswordBinding
import java.io.*

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
            resetPassword(ACCOUNT_INFO["AccountEmail"].toString(), binding.fieldNewpass.text.toString())
            //redirect to login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun resetPassword(email : String, newPass : String) {
        val toast = Toast.makeText(this, "Password Direset! (TPI BOONG)", 1.toInt())
        toast.show()
//        val oldFile = File("file:///android_asset/users.csv")
//        val newFile = File("file:///android_asset/users_temp.csv")
//
//        val reader : BufferedReader = oldFile.bufferedReader()
//        val writer : BufferedWriter = newFile.bufferedWriter()
//
//        var rowStr = "hoho" // just so it is not blank
//        while(rowStr.isNotBlank()) {
//            try {
//                rowStr = reader.readLine()
//            } catch (e : NullPointerException) {
//                break
//            }
//            val record = rowStr.split(",").toMutableList()
//            val emailFromCsv = record[1].trim()
//            if (email == emailFromCsv) {
//                record[2] = newPass
//                rowStr = "" // empty then refill
//                for (i in record.indices) {
//                    rowStr += record[i].toString()
//                    if (i < record.indices.last) {
//                        rowStr += ", "
//                    }
//                }
//                continue
//            }
//            writer.write(rowStr)
//            writer.newLine()
//        }
//
//        newFile.renameTo(oldFile)
    }

    private fun getRow(key : String) {
        val istream = assets.open("users.csv")
        val reader = istream.bufferedReader()
    }
}