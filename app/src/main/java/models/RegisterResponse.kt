package models

data class RegisterResponse(
    val cuenta: Cuenta,
    val token: String,
    val user: User
) {
    data class Cuenta(
        val apellidos: String,
        val created_at: String,
        val email: String,
        val id: Int,
        val idMultimedia: Any,
        val idUsuario: Int,
        val info: String,
        val nombre: String,
        val updated_at: String
    )

    data class User(
        val created_at: String,
        val id: Int,
        val updated_at: String,
        val username: String
    )
}