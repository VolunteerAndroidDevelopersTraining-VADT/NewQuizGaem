package ali.hrhera.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil3.load


@BindingAdapter("app:loadImage")
fun ImageView.loadImage(url: String?) {
    url?.let {
        this.load(it)

    }
}