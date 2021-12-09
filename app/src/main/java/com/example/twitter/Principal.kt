package com.example.twitter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.twitter.ui.home.InicioFragment
import com.example.twitter.ui.notifications.BuscarFragment
import com.example.twitter.ui.perfil.PerfilFragment
import kotlinx.android.synthetic.main.activity_principal.*

class Principal : AppCompatActivity(R.layout.activity_principal) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //replaceFragment(perfilView)

        val extras = intent.extras
        val token = extras?.getString("token")
        println("Token desde principal: "+ token)

        if (savedInstanceState == null) {
            val bundle = bundleOf("token" to token)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<PerfilFragment>(R.id.nav_fragment, args = bundle)
            }
        }


        nav_view.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.perfil -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<PerfilFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
                R.id.buscar -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<BuscarFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
                R.id.inicio -> {
                    val bundle = bundleOf("token" to token)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<InicioFragment>(R.id.nav_fragment, args = bundle)
                    }
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment, token : String?) {
        if(fragment != null){
            val bundle = bundleOf("token" to token)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_fragment, fragment)
            transaction.commit()
        }
    }


}