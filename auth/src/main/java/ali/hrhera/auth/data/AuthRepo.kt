package ali.hrhera.auth.data

import ali.hrhera.base.ResponseStatus
import ali.hrhera.auth.domain.UserDto
import kotlinx.coroutines.flow.MutableSharedFlow

interface AuthRepo {

    val loginFlow: MutableSharedFlow<ResponseStatus<UserDto>>
    suspend fun login(email: String, password: String)

    val registerFlow: MutableSharedFlow<ResponseStatus<Boolean>>
    suspend fun startRegister(
        username: String,
        email: String,
        password: String,
        phone: String,

        )

}