package yerakulmanov.petproject.chatbotapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import yerakulmanov.petproject.chatbotapp.Injection
import yerakulmanov.petproject.chatbotapp.repository.RoomRepository
import yerakulmanov.petproject.chatbotapp.data.ResultEvent
import yerakulmanov.petproject.chatbotapp.data.Room

class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository

    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            roomRepository.createRoom(name)
        }
        loadRooms()
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is ResultEvent.Success -> _rooms.value = result.data
                is ResultEvent.Error -> {

                }
            }
        }
    }

}