package ali.hrhera.newquizgaem

import ali.hrhera.base.BaseViewModel
import ali.hrhera.base.data.AppDataStore
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainState {
    data object OpenOnBoarding : MainState()
    data object OpenLogin : MainState()
    data object Idle : MainState()
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appDataStore: AppDataStore
) : BaseViewModel() {
    private val isFirstTime = appDataStore.get("isFirstTime", true).toStatFlow()

    val states = MutableSharedFlow<MainState>()
    fun saveIsLogin(value: Boolean) {
        viewModelScope.launch {
            appDataStore.saveCipher("isLogin", value)
        }
        if (!value) updateState(MainState.OpenLogin)
    }

    fun saveIsFirstTime(value: Boolean) {
        viewModelScope.launch {
            appDataStore.save("isFirstTime", value)
        }
    }

    init {
        updateState(MainState.Idle)
        viewModelScope.launch {
            isFirstTime.collectLatest {
                it?.let {
                    if (it) updateState(MainState.OpenOnBoarding)
                    else checkLogin()
                }
            }
        }


    }

    private fun checkLogin() {
        viewModelScope.launch {
            appDataStore.getCiphered("isLogin", false).toStatFlow()
                .collectLatest {
                    it?.let { value ->
                        if (value) updateState(MainState.Idle)
                        else updateState(MainState.OpenLogin)
                    }
                }
        }
    }

    private fun updateState(state: MainState) {
        viewModelScope.launch {
            states.emit(state)
        }
    }

}