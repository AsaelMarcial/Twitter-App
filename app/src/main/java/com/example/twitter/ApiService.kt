package com.example.twitter

import models.LoginResponse
import models.Post
import models.RegisterResponse
import models.Usuario
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import okhttp3.RequestBody


interface ApiService {

    @Multipart
    @POST("/api/register")
    fun register(@PartMap map: HashMap<String?, RequestBody?>): Call<RegisterResponse>

    @Multipart
    @POST("/api/login")
    fun login(@PartMap map: HashMap<String?, RequestBody?>): Call<LoginResponse>

    @GET("/api/getCuenta")
    fun getUser(@Header("Authorization") token: String): Call<Usuario>

    @GET("/api/misPosts")
    fun getMisPost(@Header("Authorization") token: String): Call<Post>


}