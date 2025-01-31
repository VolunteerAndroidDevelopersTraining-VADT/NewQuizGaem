package ali.hrhera.auth.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String? = ""
) : Parcelable
