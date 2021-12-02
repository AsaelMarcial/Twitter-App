package models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    var id: Int,
    @SerializedName("idUsuario")
    var idUsuario: Int,
    @SerializedName("descripcion")
    var descripcion: String,
    @SerializedName("idMultimedia")
    var idMultimedia: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("updated_at")
    var updatedAt: String
)
