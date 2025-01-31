package ali.hrhera.auth.uitl

import ali.hrhera.auth.uitl.errors.PasswordError
import ali.hrhera.auth.uitl.errors.UserNameError

fun String?.userNameValidation(): String {
    if (this.isNullOrBlank() || this.length < 2)
        throw UserNameError("User name is not valid")
    return this

}