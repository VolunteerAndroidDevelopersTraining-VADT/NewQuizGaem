package ali.hrhera.base.data

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


fun String.getKey(): ByteArray {
    return this.toByteArray(Charsets.UTF_8)
}


private const val DATASTORE_CIPHER_KEY = "dD6HofKdMGIPiUpajUuNyT1AQsn46YIL"

fun String.encrypt(): String {
    val secretKeySpec = SecretKeySpec(DATASTORE_CIPHER_KEY.getKey(), "AES")
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
    return Base64.encodeToString(cipher.doFinal(this.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
}

fun String.decrypt(): String {
    val secretKeySpec = SecretKeySpec(DATASTORE_CIPHER_KEY.getKey(), "AES")
    val cipher = Cipher.getInstance("AES/ECB/PKCSr5Padding")
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    return String(cipher.doFinal(Base64.decode(this, Base64.DEFAULT)))
}