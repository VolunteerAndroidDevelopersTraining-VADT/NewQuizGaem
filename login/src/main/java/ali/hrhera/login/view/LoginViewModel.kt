package ali.hrhera.login.view

import ali.hrhera.base.BaseViewModel
import ali.hrhera.login.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {


}