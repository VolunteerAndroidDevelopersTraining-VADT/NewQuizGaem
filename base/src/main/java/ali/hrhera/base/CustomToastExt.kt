package ali.hrhera.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


fun Fragment.showDoneToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    viewLifecycleOwner.lifecycleScope.launch {
        requireContext().shoDoneToast(msg, delay, onDismiss)
    }

}

fun Fragment.showErrorToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    viewLifecycleOwner.lifecycleScope.launch {
        requireContext().showErrorToast(msg, delay, onDismiss)
    }
}

fun Fragment.showWorningToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    viewLifecycleOwner.lifecycleScope.launch {
        requireContext().showWorningToast(msg, delay, onDismiss)
    }
}



