package yerakulmanov.petproject.chatbotapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import yerakulmanov.petproject.chatbotapp.Injection
import yerakulmanov.petproject.chatbotapp.data.Message
import yerakulmanov.petproject.chatbotapp.data.ResultEvent
import yerakulmanov.petproject.chatbotapp.data.User
import yerakulmanov.petproject.chatbotapp.repository.MessageRepository
import yerakulmanov.petproject.chatbotapp.repository.UserRepository

class MessageViewModel : ViewModel() {

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }


    fun sendMessage(text: String) {
        if (_currentUser.value != null) {
            val message = Message(
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                text = text
            )
            viewModelScope.launch {
                when (messageRepository.sendMessage(_roomId.value.toString(), message)) {
                    is ResultEvent.Success -> {
                        loadMessages()
                        Log.d("TAG", "loadCurrentUser: ${_currentUser.value}")
                    }
                    is ResultEvent.Error -> {

                    }
                }
            }
        }
    }

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }


    fun loadMessages() {
        viewModelScope.launch {
            messageRepository.getChatMessages(_roomId.value.toString())
                .collect { _messages.value = it }
        }
        Log.d("TAG", "loadMessages: ${_messages.value}")
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is ResultEvent.Success -> {
                    Log.d("TAG", "loadCurrentUser: ${result.data}")
                    _currentUser.value = result.data
                }
                is ResultEvent.Error -> {

                }

            }
        }
    }

}