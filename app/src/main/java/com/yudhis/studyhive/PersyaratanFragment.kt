package com.yudhis.studyhive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PersyaratanFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_persyaratan, container,false)

        var tvPersyaratan = view.findViewById<TextView>(R.id.tv_persyaratan)
        tvPersyaratan.text = "-Peserta diwajibkan memiliki kemauan dan niat dalam mempelajari course\n" +
                            "-Peserta diwajibkan hadir dalam course tepat waktu\n" +
                            "-Peserta tidak perlu khawatir jika masih newbie karena course bersifat dasar dan mudah dimengerti\n" +
                            "-Peserta diharapkan memiliki akses stabil ke internet dan komputer/laptop yang memadai untuk mengikuti kursus.\n" +
                            "-Peserta diharapkan memiliki kemampuan untuk menginstal dan menggunakan perangkat lunak atau aplikasi yang dibutuhkan untuk kursus (seperti editor kode atau aplikasi pengolah gambar)."
        return view
    }
}