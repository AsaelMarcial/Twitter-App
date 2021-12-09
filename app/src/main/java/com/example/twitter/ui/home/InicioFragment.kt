package com.example.twitter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.twitter.R

class  InicioFragment : Fragment(R.layout.fragment_inicio) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = requireArguments().getString("token")
        println("Token desde Homer: " + token)
    }



}