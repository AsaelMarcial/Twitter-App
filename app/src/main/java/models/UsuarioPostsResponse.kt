package models

data class UsuarioPostsResponse(
    val posts: List<Post>
) {
    data class Post(
        val created_at: String,
        val descripcion: String,
        val id: Int,
        val idMultimedia: Any,
        val idUsuario: Int,
        val updated_at: String
    )
}