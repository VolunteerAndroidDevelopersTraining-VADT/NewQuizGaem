package ali.hrhera.login.domain.usecase

import ali.hrhera.base.ResponseStatus
import ali.hrhera.login.data.LoginRepo
import ali.hrhera.login.uitl.errors.EmailError
import ali.hrhera.login.uitl.errors.PasswordError
import ali.hrhera.login.uitl.toMd5
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepo: LoginRepo
) {
    val response = loginRepo.loginFlow
    suspend operator fun invoke(username: String, password: String) {

        try {
            loginRepo.login(
                username.emailValidation(),
                password.getHashedPassword()
            )

        } catch (e: Throwable) {
            response.emit(ResponseStatus.Error(e))
        }

    }

    private fun String?.getHashedPassword(): String {
        if (this.isNullOrBlank() || this.length < 6)
            throw PasswordError("Password length must be at least 6 characters")
        return this.toMd5()

    }

    private fun String?.emailValidation(): String {
        if (this.isNullOrBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches())
            throw EmailError("Email is not valid")
        return this

    }
}