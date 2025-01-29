package ali.hrhera.login.data

import ali.hrhera.base.ResponseStatus
import ali.hrhera.login.domain.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface LoginRepo {

    val loginFlow: MutableSharedFlow<ResponseStatus<UserDto>>
    suspend fun login(username: String, password: String)
}