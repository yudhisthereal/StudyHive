package com.yudhis.studyhive

import android.app.DatePickerDialog
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
import java.util.Calendar

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val buttonSelanjutnya = findViewById<Button>(R.id.bt_selanjutnya)

        buttonSelanjutnya.setOnClickListener {
            Intent(this, SignUp2Activity::class.java).also {
                startActivity(it)
            }
        }

        val textViewSudahPunyaAkunLogin = "Sudah punya akun? Login"
        val spannableString = SpannableString(textViewSudahPunyaAkunLogin)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            }
        }
        spannableString.setSpan(clickableSpan, 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD),17,23,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = findViewById<TextView>(R.id.tv_sudah_punya_akun)
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()

        val editTextNama = findViewById<EditText>(R.id.et_nama)
        val editTextTtl  = findViewById<EditText>(R.id.et_ttl)
        val editTextNotelp = findViewById<EditText>(R.id.et_notelp)
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buttonSelanjutnya.isEnabled = editTextNama.text.isNotEmpty() &&
                        editTextTtl.text.isNotEmpty() &&
                        editTextNotelp.text.isNotEmpty()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextNama.addTextChangedListener(textWatcher)
        editTextTtl.addTextChangedListener(textWatcher)
        editTextNotelp.addTextChangedListener(textWatcher)

        editTextTtl.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, yearSelected, monthOfYear, dayOfMonth ->
                val selectedDate = String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, yearSelected)
                editTextTtl.setText(selectedDate)
            }, year, month, dayOfMonth)

            datePickerDialog.show()
        }

    }
}