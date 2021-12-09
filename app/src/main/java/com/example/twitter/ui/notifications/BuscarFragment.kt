package com.example.twitter.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.twitter.R

class BuscarFragment : Fragment(R.layout.fragment_buscar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = requireArguments().getString("token")
        println("Token desde buscar: " + token)
    }

}