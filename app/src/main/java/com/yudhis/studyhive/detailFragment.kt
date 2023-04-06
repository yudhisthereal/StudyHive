package com.yudhis.studyhive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class detailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container,false)

        val tvDetail = view.findViewById<TextView>(R.id.tv_detail)
        val fullDescription = arguments?.getString("course_full_description") ?: ""
        tvDetail.text = fullDescription

        val tvContents = view.findViewById<TextView>(R.id.tv_content)
        val courseContents = arguments?.getString("course_contents") ?: ""
        tvContents.text = courseContents

        return view
    }
    }
