package ali.hrhera.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


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


    suspend fun <T : Any> Flow<ResponseStatus<T>>.collectStatus(onData: (data: T) -> Unit) {
        this.collectLatest { status ->
            _isLoading.postValue(status is ResponseStatus.Loading)
            when (status) {
                is ResponseStatus.Error -> handleError(status.message)
                is ResponseStatus.Success -> onData(status.data)
                else -> {}
            }
        }
    }


}
