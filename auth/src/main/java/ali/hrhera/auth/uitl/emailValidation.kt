package ali.hrhera.auth.uitl

import ali.hrhera.auth.uitl.errors.EmailError
import android.util.Log

fun String?.emailValidation(): String {
        if (this.isNullOrBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches())
            throw EmailError("Email is not valid")
        return this

    }