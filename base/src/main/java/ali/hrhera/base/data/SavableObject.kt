package ali.hrhera.base.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SavableObject<T>(
    @SerializedName("Value")
    val value: T
)
