package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClosetFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val send = arguments?.getString("ID")
        println("closetFragment")
        println(send)
        return inflater.inflate(R.layout.fragment_closet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val add_closet = view.findViewById<TextView>(R.id.add_cloth)
        val ex_outer = view.findViewById<ImageView>(R.id.ex_outer)

        add_closet.setOnClickListener{
            //MainActivity에서 ID값 받아옴
            val send = arguments?.getString("ID")
            //잘 넘어왔는지 print로 확인
            println("addcloset-intent")
            println(send)
            val intent = Intent(activity, AddCloset::class.java)
            intent.putExtra("ID", send)
            startActivity(intent)
        }
        ex_outer.setOnClickListener{
            val send = arguments?.getString("ID")
            println("exouter-intent")
            println(send)
            val intent = Intent(activity, EditCloset::class.java)
            intent.putExtra("ID", send)
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<TextView>(R.id.add_cloth).setOnClickListener{
//            val intent = Intent(activity, AddCloset::class.java)
//            startActivity(intent)
//        }
//    }


}