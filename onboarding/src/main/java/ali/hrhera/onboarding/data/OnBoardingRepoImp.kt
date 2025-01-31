package ali.hrhera.onboarding.data

import ali.hrhera.base.ResponseStatus
import ali.hrhera.base.util.doTask
import ali.hrhera.onboarding.domain.OnBoardingScreenDto
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class OnBoardingRepoImp @Inject constructor() : OnBoardingRepo {
    override val onBoardingScreenResponse: MutableSharedFlow<ResponseStatus<List<OnBoardingScreenDto>>> = MutableSharedFlow()

    override suspend fun fetchOnBoardingScreen() {
        Firebase.firestore.collection("onboarding").get().addOnFailureListener {
            doTask {
                onBoardingScreenResponse.emit(ResponseStatus.Error(it))
            }
            Log.e("TAG", "addOnFailureListener: $it")

        }.addOnSuccessListener {
            doTask {
                try {
                    val items = it.documents.mapNotNull { it.toObject(OnBoardingScreenDto::class.java) }
                    onBoardingScreenResponse.emit(ResponseStatus.Success(items))
                } catch (e: Throwable) {
                    Log.e("TAG", "addOnSuccessListener: $e")
                    onBoardingScreenResponse.emit(ResponseStatus.Error(e))
                }
            }
        }

    }

    fun init() {
        listOf(
            OnBoardingScreenDto(
                id = 0,
                title = "title 1",
                description = "description 1",
                image = "https://www.svgrepo.com/download/513272/book-closed.svg",
                color = "color 1"
            ),
            OnBoardingScreenDto(
                id = 1,
                title = "with eggs drink ginger sauce.11",
                description = "description 2",
                image = "https://www.svgrepo.com/download/513272/book-closed.svg",
                color = "color 2"
            ),
            OnBoardingScreenDto(
                id = 2,
                title = "with eggs drink ginger sauce.",
                description = "oddly infiltrate a planet. Hercle, compater secundus!.",
                image = "https://www.svgrepo.com/download/513272/book-closed.svg",
                color = "color 3"
            )
        ).forEach {
            /*Firebase.firestore
                .collection("onboarding")
                .add(it)*/
        }

    }
}