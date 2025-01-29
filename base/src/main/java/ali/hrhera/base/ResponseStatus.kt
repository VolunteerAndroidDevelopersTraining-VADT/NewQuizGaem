package ali.hrhera.base

sealed class ResponseStatus<out T : Any> {

    data object Loading : ResponseStatus<Nothing>()
    data object Empty : ResponseStatus<Nothing>()
    data class Success<T : Any>(val data: T) : ResponseStatus<T>()
    data class Error(val message: Throwable) : ResponseStatus<Nothing>()
}