package com.yudhis.studyhive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class PembicaraFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pembicara, container,false)

        val tvPembicara1 = view.findViewById<TextView>(R.id.tv_pembicara1)
        val namaPembicara1 = arguments?.getString("course_pembicara1") ?: ""
        tvPembicara1.text = namaPembicara1

        val tvPembicara2 = view.findViewById<TextView>(R.id.tv_pembicara2)
        val namaPembicara2 = arguments?.getString("course_pembicara2") ?: ""
        tvPembicara2.text = namaPembicara2

        return view
    }
}