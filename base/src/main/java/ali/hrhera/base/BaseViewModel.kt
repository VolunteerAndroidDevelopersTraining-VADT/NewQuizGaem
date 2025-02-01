package ali.hrhera.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn


abstract class BaseViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    private fun handleError(throwable: Throwable) {
        _errorMessage.value = throwable.message
    }

    fun showErrorMessage(throwable: Throwable) {
        _errorMessage.value = throwable.message
    }


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun <T : Any> Flow<ResponseStatus<T>>.collectStatus(
        onError: ((message: Throwable) -> Unit)? = null,
        onData: (data: T) -> Unit,
    ) {
        this.collectLatest { status ->
            _isLoading.postValue(status is ResponseStatus.Loading)
            when (status) {
                is ResponseStatus.Error -> onError?.invoke(status.message) ?: handleError(status.message)
                is ResponseStatus.Success -> onData(status.data)
                else -> {}
            }
        }
    }


    fun <T> Flow<T>.toStatFlow(defaultValue: T): StateFlow<T> {
        return this.stateIn(viewModelScope, SharingStarted.Eagerly, defaultValue)
    }
    fun <T> Flow<T?>.toStatFlow(): StateFlow<T?> {
        return this.stateIn(viewModelScope, SharingStarted.Eagerly, null)
    }
}
