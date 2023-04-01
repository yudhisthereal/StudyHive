package com.yudhis.studyhive

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.util.*

class editInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_info, container,false)
        val btnSimpan = view.findViewById<Button>(R.id.bt_simpan)
        btnSimpan.setOnClickListener {
            val etNama = view.findViewById<EditText>(R.id.et_nama)
            val inputNama = etNama.text.toString()
            val etEmail = view.findViewById<EditText>(R.id.et_email)
            val inputEmail = etEmail.text.toString()
            val etNotelp = view.findViewById<EditText>(R.id.et_notelp)
            val inputNotelp = etNotelp.text.toString()
            val etAlamat = view.findViewById<EditText>(R.id.et_alamat)
            val inputAlamat = etAlamat.text.toString()
            val etTanggalLahir = view.findViewById<EditText>(R.id.et_tanggallahir)

            etTanggalLahir.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(requireContext(), { _, yearSelected, monthOfYear, dayOfMonth ->
                    val selectedDate = String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, yearSelected)
                    etTanggalLahir.setText(selectedDate)
                }, year, month, dayOfMonth)

                datePickerDialog.show()
            }
            val inputTanggalLahir = etTanggalLahir.text.toString()

            val bundle = Bundle()
            bundle.putString("alamat",inputAlamat)
            bundle.putString("notelp",inputNotelp)
            bundle.putString("email",inputEmail)
            bundle.putString("nama",inputNama)
            bundle.putString("tanggallahir",inputTanggalLahir)

            val fragment = BaseInfoAkunFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.editfragment,fragment)?.commit()
        }
        return view
    }

}