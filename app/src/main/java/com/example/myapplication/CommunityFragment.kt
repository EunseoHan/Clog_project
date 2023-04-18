package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.CommunityWrite
import com.example.myapplication.R

class CommunityFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val go_to_community_write = view.findViewById<TextView>(R.id.go_to_community_write)
        val ex_community1 = view.findViewById<LinearLayout>(R.id.ex_community1)

        go_to_community_write.setOnClickListener{
            val intent = Intent(activity, CommunityWrite::class.java)
            startActivity(intent)
        }
        ex_community1.setOnClickListener{
            val intent = Intent(activity, CommunityDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)
    }
}