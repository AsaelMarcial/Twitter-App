package models

import com.google.gson.annotations.SerializedName

data class Usuario(
     @SerializedName("id")
     var id: Int,
     @SerializedName("idUsuario")
     var idUsuario: Int,
     @SerializedName("idMultimedia")
     var idMultimedia: Int,
     @SerializedName("nombre")
     var nombre: String,
     @SerializedName("apellidos")
     var apellidos: String,
     @SerializedName("email")
     var email: String,
     @SerializedName("info")
     var info: String,
     @SerializedName("created_at")
     var createdAt: String,
     @SerializedName("updated_at")
     var updatedAt: String


)
