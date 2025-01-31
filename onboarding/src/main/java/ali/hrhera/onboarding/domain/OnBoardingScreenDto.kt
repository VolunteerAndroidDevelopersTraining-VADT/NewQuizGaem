package ali.hrhera.onboarding.domain

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Keep
@Parcelize
data class OnBoardingScreenDto(
    val id: Int, val image: String?,
    val title: String,
    val description: String,
    val color: String? = null
) : Parcelable {

    constructor() : this(0, "", "", "")

    @IgnoredOnParcel
    @Exclude
    var parsedColor: Int = Color.BLUE
}
