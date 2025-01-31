package ali.hrhera.auth.fatures.register.view

import ali.hrhera.base.BaseViewModel
import ali.hrhera.auth.domain.UserDto
import ali.hrhera.auth.domain.usecase.RegisterUseCase
import ali.hrhera.auth.uitl.errors.EmailError
import ali.hrhera.auth.uitl.errors.PasswordError
import ali.hrhera.auth.uitl.errors.PhoneError
import ali.hrhera.auth.uitl.errors.UserNameError
import ali.hrhera.base.ResponseStatus
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    val nameError = ObservableField<String?>()
    val passwordError = ObservableField<String?>()
    val phoneError = ObservableField<String?>()
    val emailError = ObservableField<String?>()

    fun startRegisterNewUser(
        email: String?, password: String?, phone: String?, userName: String?
    ) {
        resetErrors()
        viewModelScope.launch {
            registerUseCase(
                username = userName, phone = phone, email = email, password = password
            )
        }
    }

    private fun resetErrors() {
        nameError.set(null)
        passwordError.set(null)
        phoneError.set(null)
        emailError.set(null)
    }

    val registerResponse = MutableLiveData<Boolean>()
    val loading = registerUseCase.response.map { it is ResponseStatus.Loading }.asLiveData()

    init {
        viewModelScope.launch {
            registerUseCase.response.collectStatus({
                when (it) {
                    is PhoneError -> phoneError.set(it.message)
                    is PasswordError -> passwordError.set(it.message)
                    is UserNameError -> nameError.set(it.message)
                    is EmailError -> emailError.set(it.message)
                    else -> showErrorMessage(it)
                }
            }) {
                registerResponse.postValue(it)
            }
        }

    }

}