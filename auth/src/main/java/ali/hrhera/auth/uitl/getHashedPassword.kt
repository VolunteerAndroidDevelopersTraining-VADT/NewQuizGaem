package ali.hrhera.auth.uitl

import ali.hrhera.auth.uitl.errors.PasswordError

fun String?.getHashedPassword(): String {
        if (this.isNullOrBlank() || this.length < 6)
            throw PasswordError("Password length must be at least 6 characters")
        return this.toMd5()

    }