package ali.hrhera.base

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


fun Fragment.showToast(msg: String?) {
    requireActivity().showToast(msg)
}

fun Fragment.showToast(@StringRes msg: Int) {
    requireActivity().showToast(msg)
}


fun Activity.showToast(msg: String?) {
    msg?.takeIf { it.isNotBlank() }?.let {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Activity.showToast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
