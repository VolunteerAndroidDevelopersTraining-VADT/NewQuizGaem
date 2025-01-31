package ali.hrhera.newquizgaem

import ali.hrhera.base.BaseViewModel
import ali.hrhera.base.data.AppDataStore
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appDataStore: AppDataStore
) : BaseViewModel() {

    private val isLogin = appDataStore.get("isLogin", false).asLiveData()
    private val isFirstTime = appDataStore.get("isFirstTime", true).asLiveData()

    private val _states: MutableStateFlow<Pair<Boolean, Boolean>> = MutableStateFlow(Pair(true, false))
    val states = _states.asLiveData()
    fun saveIsLogin(value: Boolean) {
        viewModelScope.launch {
            appDataStore.save("isLogin", value)
        }
    }

    fun saveIsFirstTime(value: Boolean) {
        viewModelScope.launch {
            appDataStore.save("isFirstTime", value)
        }
    }

    init {
        isFirstTime.observeForever {
            _states.value = (Pair(it, isLogin.value ?: false))
        }
        isLogin.observeForever {
            _states.value = Pair(isFirstTime.value ?: true, it)
        }
    }

}