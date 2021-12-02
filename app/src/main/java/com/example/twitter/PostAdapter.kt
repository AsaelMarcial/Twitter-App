package com.example.twitter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import models.Post

class PostAdapter(private val context: Activity, internal var datas: List<Post>):ArrayAdapter<Post>(context, R.layout.tweet_item, datas) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.tweet_item, null, true)

        val nombre = listViewItem.findViewById(R.id.lblNickname) as TextView
        val usuario = listViewItem.findViewById(R.id.lblInfo) as TextView
        val tweet = listViewItem.findViewById(R.id.lblTweet) as TextView


        val data = datas[position]
        nombre.text = data.idUsuario.toString()
        usuario.text = "Tu"
        tweet.text = data.descripcion
        return listViewItem
    }
}