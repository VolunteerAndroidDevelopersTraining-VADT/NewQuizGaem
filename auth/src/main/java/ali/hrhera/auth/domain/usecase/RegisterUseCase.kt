package ali.hrhera.auth.domain.usecase

import ali.hrhera.base.ResponseStatus
import ali.hrhera.auth.data.AuthRepo
import ali.hrhera.auth.uitl.emailValidation
import ali.hrhera.auth.uitl.errors.EmailError
import ali.hrhera.auth.uitl.errors.PasswordError
import ali.hrhera.auth.uitl.getHashedPassword
import ali.hrhera.auth.uitl.phoneValidation
import ali.hrhera.auth.uitl.toMd5
import ali.hrhera.auth.uitl.userNameValidation
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepo: AuthRepo
) {
    val response = authRepo.registerFlow
    suspend operator fun invoke(
        username: String?,
        phone: String?,
        email: String?,
        password: String?
    ) {

        try {
            authRepo.startRegister(
                username = username.userNameValidation(),
                email = email.emailValidation(),
                phone = phone.phoneValidation(),
                password = password.getHashedPassword(),

                )

        } catch (e: Throwable) {
            response.emit(ResponseStatus.Error(e))
        }

    }


}