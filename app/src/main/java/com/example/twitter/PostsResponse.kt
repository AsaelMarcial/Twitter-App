package com.example.twitter

class PostsResponse : ArrayList<PostsResponse.PostsResponseItem>(){
    data class PostsResponseItem(
        val created_at: String,
        val descripcion: String,
        val id: Int,
        val idMultimedia: Int,
        val idUsuario: Int,
        val updated_at: String
    )
}