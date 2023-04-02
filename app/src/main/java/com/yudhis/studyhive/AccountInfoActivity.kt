package com.yudhis.studyhive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        val tvKembali = findViewById<TextView>(R.id.tv_kembali)
//        val btnUbahPassword = findViewById<Button>(R.id.bt_ubah_password)

        tvKembali.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

//        btnUbahPassword.setOnClickListener {
//            Intent(this,ChangePasswordAccountInfoActivity::class.java).also {
//                startActivity(it)
//            }
//        }

        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val displayName = user?.displayName
        val namaLengkap = findViewById<TextView>(R.id.tv_namalengkap)
        namaLengkap.text = displayName

        val editInfoAkun = findViewById<Button>(R.id.bt_editinfoakun)

        val editInfoFragment = editInfoFragment()
        val baseInfoAkunFragment = BaseInfoAkunFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.editfragment, baseInfoAkunFragment)
            commit()
        }

        editInfoAkun.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.editfragment, editInfoFragment)
                commit()
            }
        }

    }
}