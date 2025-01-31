package ali.hrhera.auth.data

import ali.hrhera.auth.domain.UserDto
import ali.hrhera.auth.domain.UserRegisterRequest
import ali.hrhera.auth.uitl.errors.AlreadyUserError
import ali.hrhera.base.ResponseStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepoImp @Inject constructor() : AuthRepo {

    override val loginFlow: MutableSharedFlow<ResponseStatus<UserDto>> = MutableSharedFlow()

    override suspend fun login(email: String, password: String) {
        loginFlow.emit(ResponseStatus.Loading)

        Firebase.firestore.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password).get()
            .addOnFailureListener {
                doTask {
                    loginFlow.emit(ResponseStatus.Error(it))
                }
            }.addOnSuccessListener {
                if (it.isEmpty) {
                    doTask {
                        loginFlow.emit(ResponseStatus.Error(Throwable("User not found")))
                    }
                } else {
                    doTask {
                        try {
                            loginFlow.emit(
                                ResponseStatus.Success(
                                    it.documents.first().toObject(UserDto::class.java)!!
                                )
                            )
                        } catch (e: Throwable) {
                            loginFlow.emit(
                                ResponseStatus.Error(e)
                            )
                        }
                    }
                }
            }


    }

    private fun doTask(task: suspend () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            task()
        }
    }


    override val registerFlow: MutableSharedFlow<ResponseStatus<Boolean>> = MutableSharedFlow()

    override suspend fun startRegister(username: String, email: String, password: String, phone: String) {

        registerFlow.emit(ResponseStatus.Loading)
        findIfUserExist(email) {
            Firebase.firestore.collection("users")
                .add(
                    UserRegisterRequest(
                        username = username,
                        email = email,
                        phone = phone,
                        password = password
                    )
                ).addOnFailureListener {
                    doTask {
                        registerFlow.emit(ResponseStatus.Error(it))
                    }
                }.addOnSuccessListener {
                    doTask {
                        registerFlow.emit(
                            ResponseStatus.Success(true)
                        )
                    }
                }
        }

    }


    private suspend fun findIfUserExist(email: String, onDone: () -> Unit) {
        Firebase.firestore.collection("users")
            .whereEqualTo("username", email)
            .get().addOnFailureListener {
                doTask {
                    registerFlow.emit(ResponseStatus.Error(it))
                }
            }.addOnSuccessListener {
                if (it.isEmpty) {
                    onDone()
                } else {
                    doTask {
                        registerFlow.emit(ResponseStatus.Error(AlreadyUserError("User already exist")))
                    }
                }
            }

    }
}