package com.example.binapplication

import android.util.Log
import androidx.lifecycle.*
import com.example.binapplication.network.BinApi
import com.example.binapplication.data.JSON.CardData
import com.example.binapplication.data.database.Number
import com.example.binapplication.data.database.NumberDao
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

enum class BinApiStatus{
    LOADING,
    ERROR,
    DONE
}
class BinViewModel(private val numberDao: NumberDao): ViewModel() {

    val _history: LiveData<List<String>> = numberDao.getHistory().asLiveData()

    val history: List<String>
        get() = _history.value ?: mutableListOf()

    private val _status = MutableLiveData<BinApiStatus>()
    val status: LiveData<BinApiStatus> = _status

    var statusMessage = MutableLiveData<String>()

    private val _cardData = MutableLiveData<CardData>()

    val cardData: LiveData<CardData> = _cardData

    private fun insertNumber(number: Number) {
        viewModelScope.launch {
            if (!history.contains(number.number)) numberDao.insert(number)
        }
    }


        fun getCardData(number: String) {
            _status.value = BinApiStatus.LOADING
            viewModelScope.launch {
                try {
                    val result = BinApi.retrofitService.getCardData(number)
                    insertNumber(Number(number = number))
                    _cardData.value = result
                    _status.value = BinApiStatus.DONE
                } catch (e: HttpException) {
                    Log.e("BinViewModel", "${e.message.toString()} ${e.javaClass}")
                    _status.value = BinApiStatus.ERROR
                    statusMessage.value = "Invalid card number"
                }catch (e: UnknownHostException) {
                    Log.e("BinViewModel", "${e.message.toString()} ${e.javaClass}")
                    _status.value = BinApiStatus.ERROR
                    statusMessage.value = "No internet connection"
                }catch (e: JsonEncodingException){
                    Log.e("BinViewModel", "${e.message.toString()} ${e.javaClass}")
                    _status.value = BinApiStatus.ERROR
                    statusMessage.value = "You should put a number"
                }catch (e: Exception){
                    Log.e("BinViewModel", "${e.message.toString()} ${e.javaClass}")
                    _status.value = BinApiStatus.ERROR
                    statusMessage.value = e.message
                }

            }
        }
}

class BinViewModelFactory(private val numberDao: NumberDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BinViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return BinViewModel(numberDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}