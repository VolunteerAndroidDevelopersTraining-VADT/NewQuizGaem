package ali.hrhera.login.uitl

import java.math.BigInteger

fun String.toMd5(): String {
        val md5 = java.security.MessageDigest.getInstance("MD5")
        return BigInteger(1, md5.digest(toByteArray())).toString(16).padStart(32, '0')
    }