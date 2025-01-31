package ali.hrhera.onboarding.domain

import ali.hrhera.base.ResponseStatus
import ali.hrhera.onboarding.data.OnBoardingRepo
import android.graphics.Color
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FetchOnBoardingScreenUseCase @Inject constructor(
    private val repo: OnBoardingRepo
) {

    val response = repo.onBoardingScreenResponse

    suspend operator fun invoke() {
        repo.fetchOnBoardingScreen()
        response.collectLatest { response ->
            if (response is ResponseStatus.Success) {
                repo.onBoardingScreenResponse.emit(ResponseStatus.Success(onBoardingScreenData(response)))
            } else this.response.emit(response)
        }
    }

    private fun onBoardingScreenData(response: ResponseStatus.Success<List<OnBoardingScreenDto>>) = response.data.map { item ->
        item.copy().apply {
            try {
                if (item.color?.hexColorValidation() == true) {
                    this.parsedColor = Color.parseColor(item.color)
                } else this.parsedColor = ali.hrhera.base.R.color.blue
            } catch (e: Throwable) {
                this.parsedColor = ali.hrhera.base.R.color.blue
            }
        }
    }


    private fun String?.hexColorValidation(): Boolean {
        val regex = Regex("^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$")
        return regex.matches(this ?: "")
    }

}