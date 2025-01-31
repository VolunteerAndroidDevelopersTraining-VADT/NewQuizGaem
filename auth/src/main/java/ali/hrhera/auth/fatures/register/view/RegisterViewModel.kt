package ali.hrhera.auth.fatures.register.view

import ali.hrhera.base.BaseViewModel
import ali.hrhera.auth.domain.UserDto
import ali.hrhera.auth.domain.usecase.RegisterUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    fun startRegisterNewUser(
        email: String?,
        password: String?,
        phone: String?,
        userName: String?
    ) {
        viewModelScope.launch {
            registerUseCase(
                username = userName,
                phone = phone,
                email = email,
                password = password
            )
        }
    }

    val registerResponse = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            registerUseCase.response.collectStatus {
                registerResponse.postValue(it)
            }
        }
    }

}