package com.example.twitter

import layout.BuscarUsuarioResponse
import models.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import okhttp3.RequestBody

import models.RegisterResponse.User

import retrofit2.http.Multipart





interface ApiService {

    @Multipart
    @POST("/api/register")
    fun register(@PartMap map: HashMap<String?, RequestBody?>): Call<RegisterResponse>

    @Multipart
    @POST("/api/login")
    fun login(@PartMap map: HashMap<String?, RequestBody?>): Call<LoginResponse>

    @GET("/api/getCuenta")
    fun getUser(@Header("Authorization") token: String): Call<Usuario>

    @GET("/api/getPostSeguidos")
    fun getPostsSeguidos(@Header("Authorization") token: String): Call<List<Post>>

    @GET("/api/misPosts")
    fun getMisPost(@Header("Authorization") token: String): Call<List<Post>>

    @Multipart
    @POST("/api/createPost")
    fun createPost(@Header("Authorization") token: String, @Part("descripcion") desc: String): Call<CreatePostResponse>

    @GET("/api/{idUsuario}/mostrarCuenta")
    fun mostarCuenta(@Path("idUsuario") id: Int): Call<MostrarCuentaResponse>

    @GET("/api/{idSeguido}/seguirUsuario")
    fun seguirCuenta(@Path("idUsuario") id: Int): Call<MessageResponse>

    @GET("/api/{username}/buscarUsuario")
    fun buscarUsuario(@Path("username") id: String): Call<BuscarUsuarioResponse>

    @GET("/api/allPosts")
    fun allPosts(): Call<List<Post>>

    @GET("/api/{palabra}/buscarPublicacion")
    fun buscarPalabra(@Path("palabra") palabra: String): Call<BuscarResponse>

}