package com.example.binapplication

import android.util.Log
import androidx.lifecycle.*
import com.example.binapplication.network.BinApi
import com.example.binapplication.data.JSON.CardData
import com.example.binapplication.data.database.Number
import com.example.binapplication.data.database.NumberDao
import kotlinx.coroutines.launch

class BinViewModel(private val numberDao: NumberDao): ViewModel() {

    val _history: LiveData<List<String>> = numberDao.getHistory().asLiveData()

    val history: List<String>
        get() = _history.value ?: mutableListOf()

    private val _cardData = MutableLiveData<CardData>()

    val cardData: LiveData<CardData> = _cardData

    private fun insertNumber(number: Number) {
        viewModelScope.launch {
            if (!history.contains(number.number)) numberDao.insert(number)
        }
    }


        fun getCardData(number: String) {
            viewModelScope.launch {
                try {
                    insertNumber(Number(number = number))
                    val request = number.toInt()
                    val result = BinApi.retrofitService.getCardData(request)
                    _cardData.value = result
                } catch (e: Exception) {
                    Log.e("BinViewModel", e.message.toString())
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