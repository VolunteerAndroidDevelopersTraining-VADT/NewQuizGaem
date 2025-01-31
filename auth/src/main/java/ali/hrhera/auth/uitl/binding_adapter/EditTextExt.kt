package ali.hrhera.auth.uitl.binding_adapter

import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("app:errorText")
fun EditText.setErrorText(error: String?) {
    error?.let {
        this.error = it
    }
}