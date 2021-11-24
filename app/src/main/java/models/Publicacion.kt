package models

data class Publicacion(
    val idPublicacion: Int,
    val fechaPub: String,
    val horaPub: String,
    val idMultimedia: Int,
    val descripcion: String
)
