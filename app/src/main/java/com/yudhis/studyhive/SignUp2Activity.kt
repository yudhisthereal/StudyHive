package com.yudhis.studyhive

import android.app.Dialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SignUp2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        val textViewSudahPunyaAkunLogin = "Sudah punya akun? Login"
        val spannableString = SpannableString(textViewSudahPunyaAkunLogin)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignUp2Activity, LoginActivity::class.java))
            }
        }
        spannableString.setSpan(clickableSpan, 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD),17,23,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = findViewById<TextView>(R.id.tv_sudah_punya_akun)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()

        val btnBuatAkun = findViewById<Button>(R.id.bt_buat_akun)
        btnBuatAkun.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.activity_sign_up2_popup)

            val btnKembaliLogin = dialog.findViewById<Button>(R.id.bt_kembali_login)
            btnKembaliLogin.setOnClickListener {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
            dialog.show()
        }

        val editTextAlamat = findViewById<EditText>(R.id.et_alamat)
        val editTextEmail  = findViewById<EditText>(R.id.et_email)
        val editTextPassword = findViewById<EditText>(R.id.et_pass)
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnBuatAkun.isEnabled = editTextAlamat.text.isNotEmpty() &&
                        editTextEmail.text.isNotEmpty() &&
                        editTextPassword.text.isNotEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextAlamat.addTextChangedListener(textWatcher)
        editTextEmail.addTextChangedListener(textWatcher)
        editTextPassword.addTextChangedListener(textWatcher)



    }
}