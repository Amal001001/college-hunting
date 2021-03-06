package com.example.amal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    lateinit var btnApi: Button
    lateinit var btnDb: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        btnApi = view.findViewById(R.id.btnApi)
        btnApi.setOnClickListener { Navigation.findNavController(view).navigate(R.id.apiFragment) }

        btnDb = view.findViewById(R.id.btnDb)
        btnDb.setOnClickListener { Navigation.findNavController(view).navigate(R.id.dbFragment) }

        return view
    }

}