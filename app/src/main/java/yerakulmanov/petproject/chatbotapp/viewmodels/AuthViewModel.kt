package yerakulmanov.petproject.chatbotapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yerakulmanov.petproject.chatbotapp.Injection
import yerakulmanov.petproject.chatbotapp.repository.UserRepository
import yerakulmanov.petproject.chatbotapp.data.ResultEvent

class AuthViewModel : ViewModel() {
    private val userRepository: UserRepository

    private val _authResultEvent = MutableLiveData<ResultEvent<Boolean>>()
    val authResultEvent: LiveData<ResultEvent<Boolean>> get() = _authResultEvent

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }


    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authResultEvent.value = userRepository.signUp(email, password, firstName, lastName)
            Log.d("TAG1", "signUp: ${_authResultEvent.value}")
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit ) {
        viewModelScope.launch {
            _authResultEvent.value = userRepository.login(email, password, onSuccess)
            Log.d("TAG2", "signUp: ${_authResultEvent.value}")
        }
    }

}