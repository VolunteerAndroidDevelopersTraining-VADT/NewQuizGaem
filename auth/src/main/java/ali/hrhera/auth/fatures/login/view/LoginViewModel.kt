package ali.hrhera.auth.fatures.login.view

import ali.hrhera.base.BaseViewModel
import ali.hrhera.auth.domain.UserDto
import ali.hrhera.auth.domain.usecase.LoginUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    fun startLogin(email: String?, password: String?) {
        viewModelScope.launch {
            loginUseCase(email, password)
        }
    }

    val loginResponse = MutableLiveData<Pair<Boolean, UserDto>>()

    init {
        viewModelScope.launch {
            loginUseCase.response.collectStatus {
                loginResponse.postValue(Pair(true, it))
            }
        }
    }

}