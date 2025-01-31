package ali.hrhera.base.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun doTask(dispatcher: CoroutineDispatcher = Dispatchers.IO, task: suspend () -> Unit) {
    CoroutineScope(dispatcher).launch {
        task()
    }
}
