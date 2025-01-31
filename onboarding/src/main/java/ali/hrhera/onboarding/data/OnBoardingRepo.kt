package ali.hrhera.onboarding.data

import ali.hrhera.base.ResponseStatus
import ali.hrhera.onboarding.domain.OnBoardingScreenDto
import kotlinx.coroutines.flow.MutableSharedFlow

interface OnBoardingRepo {
    val onBoardingScreenResponse: MutableSharedFlow<ResponseStatus<List<OnBoardingScreenDto>>>
    suspend fun fetchOnBoardingScreen()
}