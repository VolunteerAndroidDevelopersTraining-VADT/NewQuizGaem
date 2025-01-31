package ali.hrhera.onboarding.ui.main_view

import ali.hrhera.base.BaseViewModel
import ali.hrhera.base.ResponseStatus
import ali.hrhera.onboarding.domain.FetchOnBoardingScreenUseCase
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
@Inject constructor(
    private val fetchOnBoardingScreen: FetchOnBoardingScreenUseCase
) : BaseViewModel() {

    val adapter = OnBoardingAdapter()

    init {
        viewModelScope.launch {
            fetchOnBoardingScreen()
        }

        viewModelScope.launch {
            fetchOnBoardingScreen.response.collectStatus {
                adapter.submitList(it)
            }
        }

    }

    val loading = fetchOnBoardingScreen.response.map { it is ResponseStatus.Loading }.asLiveData()
}