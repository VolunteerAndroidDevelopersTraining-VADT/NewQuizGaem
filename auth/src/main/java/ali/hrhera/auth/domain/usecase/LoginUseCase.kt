package ali.hrhera.auth.domain.usecase

import ali.hrhera.auth.data.AuthRepo
import ali.hrhera.auth.uitl.emailValidation
import ali.hrhera.auth.uitl.getHashedPassword
import ali.hrhera.base.ResponseStatus
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    val response = authRepo.loginFlow
    suspend operator fun invoke(username: String?, password: String?) {

        try {
            authRepo.login(
                username.emailValidation(),
                password.getHashedPassword()
            )

        } catch (e: Throwable) {
            response.emit(ResponseStatus.Error(e))
        }

    }


}