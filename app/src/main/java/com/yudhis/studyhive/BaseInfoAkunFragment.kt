package com.yudhis.studyhive

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class BaseInfoAkunFragment : Fragment(R.layout.fragment_base_info_akun) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base_info_akun, container,false)
        val args = this.arguments
        val btnUbahPassword = view.findViewById<Button>(R.id.bt_ubah_password)
        btnUbahPassword.setOnClickListener {
            Intent(requireContext(),ChangePasswordAccountInfoActivity::class.java).also {
                startActivity(it)
            }
        }
        val inputDataNama = args?.getString("nama")
        val inputDataEmail = args?.getString("email")
        val inputDataNotelp = args?.getString("notelp")
        val inputDataAlamat = args?.getString("alamat")
        val inputDataTanggalLahir = args?.getString("tanggallahir")

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        if(inputDataNama != null) {
            tvNama.text = inputDataNama
        } else {
            tvNama.text = "Nama Lengkap :"
        }
        val tvEmail = view.findViewById<TextView>(R.id.tv_email)
        if(inputDataEmail != null) {
            tvEmail.text = inputDataEmail
        } else {
            tvEmail.text = "Email :"
        }
        val tvNotelp = view.findViewById<TextView>(R.id.tv_notelp)
        if(inputDataNotelp != null) {
            tvNotelp.text = inputDataNotelp
        } else {
            tvNotelp.text = "No telp :"
        }
        val tvAlamat = view.findViewById<TextView>(R.id.tv_alamat)
        if(inputDataAlamat != null) {
            tvAlamat.text = inputDataAlamat
        } else {
            tvAlamat.text = "Alamat :"
        }
        val tvTanggalLahir = view.findViewById<TextView>(R.id.tv_tanggallahir)
        if(inputDataTanggalLahir != null) {
            tvTanggalLahir.text = inputDataTanggalLahir
        } else {
            tvTanggalLahir.text = "Tanggal Lahir :"
        }
        return view

    }
}