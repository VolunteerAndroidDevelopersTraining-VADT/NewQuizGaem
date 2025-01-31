package ali.hrhera.auth.uitl

import ali.hrhera.auth.uitl.errors.PasswordError
import ali.hrhera.auth.uitl.errors.PhoneError
import android.util.Log

fun String?.phoneValidation(): String {
    if (this.isNullOrBlank() || this.length != 11)
        throw PhoneError("Phone number is not valid")
    return this

}