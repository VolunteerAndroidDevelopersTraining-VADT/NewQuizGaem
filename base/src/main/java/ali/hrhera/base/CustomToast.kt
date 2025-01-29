package ali.hrhera.base

import ali.hrhera.base.databinding.DialogCusomeToastBinding
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

class CustomToast(context: Context) : Dialog(context) {
    private val binding by lazy {
        DialogCusomeToastBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun showErrorToast(msg: String): Dialog {
        binding.apply {
            parent.backgroundTintList = ContextCompat.getColorStateList(context, R.color.red)?.apply {
                withAlpha(25)
            }
            message.text = msg
            icon.setImageResource(R.drawable.ic_error)
        }
        return this

    }

    fun showDoneToast(msg: String): Dialog {
        binding.apply {
            parent.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)?.apply {
                withAlpha(25)
            }
            message.text = msg
            icon.setImageResource(R.drawable.ic_done)
        }
        return this
    }

    fun showWorningToast(msg: String): Dialog {
        binding.apply {
            parent.backgroundTintList = ContextCompat.getColorStateList(context, R.color.yellow)
            message.text = msg
            icon.setImageResource(R.drawable.ic_info)
        }
        return this

    }


}

suspend fun Context.shoDoneToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    val dialog = CustomToast(this)
        .showDoneToast(msg)
    dialog.show()

    delay(delay)
    dialog.dismiss()
    onDismiss?.invoke()
}

suspend fun Context.showErrorToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    val dialog = CustomToast(this)
        .showErrorToast(msg)
    dialog.show()

    delay(delay)
    dialog.dismiss()
    onDismiss?.invoke()
}

suspend fun Context.showWorningToast(msg: String, delay: Long = 2000L, onDismiss: (() -> Unit)? = null) {
    val dialog = CustomToast(this)
        .showErrorToast(msg)
    dialog.show()
    delay(delay)
    dialog.dismiss()
    onDismiss?.invoke()
}