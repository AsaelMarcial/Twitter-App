package models

data class BuscarResponse(
    val posts: List<Post>
) {
    data class Post(
        val created_at: String,
        val descripcion: String,
        val id: Int,
        val idMultimedia: Int,
        val idUsuario: Int,
        val updated_at: String
    )
}