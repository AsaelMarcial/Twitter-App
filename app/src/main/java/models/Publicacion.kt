package models

data class Publicacion(
    val id: Int,
    val idUsuario: Int,
    val descripcion: String,
    val idMultimedia: Int,
    val createdAt: String,
    val updatedAt: String
    )
