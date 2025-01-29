package ali.hrhera.login.data

import ali.hrhera.base.ResponseStatus
import ali.hrhera.login.domain.UserDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.tasks.await
import java.math.BigInteger
import javax.inject.Inject

class LoginRepoImp @Inject constructor() : LoginRepo {

    override val loginFlow: MutableSharedFlow<ResponseStatus<UserDto>> = MutableSharedFlow()

    override suspend fun login(username: String, password: String) {
        loginFlow.emit(ResponseStatus.Loading)
        val result =
            Firebase.firestore.collection("users")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get().await()
        if (result.isEmpty) {
            loginFlow.tryEmit(ResponseStatus.Error(Throwable("User not found")))
            return
        }
        loginFlow.tryEmit(ResponseStatus.Success(result.documents.first().toObject(UserDto::class.java)!!))

    }


}