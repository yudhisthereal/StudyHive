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

        //Membuat tombol kembali menuju main activity
        val tvKembali = findViewById<TextView>(R.id.tv_kembali)
        tvKembali.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        //menyesuaikan nama pada info akun dengan nama pada saat login
        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val displayName = user?.displayName
        val namaLengkap = findViewById<TextView>(R.id.tv_namalengkap)
        namaLengkap.text = displayName

        //Menampilkan fragmen editinfoakun dan fragmen baseinfoakun
        val editInfoAkun = findViewById<Button>(R.id.bt_editinfoakun)
        val editInfoFragment = editInfoFragment()
        val baseInfoAkunFragment = BaseInfoAkunFragment()

        //halaman awal frame layout(edit fragment) berupa base info fragmen
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.editfragment, baseInfoAkunFragment)
            commit()
        }

        //mengganti frame layout(edit fragment) dengan edit info fragmen
        editInfoAkun.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.editfragment, editInfoFragment)
                commit()
            }
        }

    }
}