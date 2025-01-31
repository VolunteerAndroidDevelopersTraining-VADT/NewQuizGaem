package ali.hrhera.auth.domain

data class UserRegisterRequest(
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String? = ""
)
