package com.example.binapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binapplication.network.BinApi
import com.example.binapplication.network.data.CardData
import kotlinx.coroutines.launch

class BinViewModel: ViewModel() {

    private var _history = mutableListOf<String>()
    val history: MutableList<String>
        get() = _history

    private val _cardData = MutableLiveData<CardData>()

    val cardData: LiveData<CardData> = _cardData


    fun getCardData(number: String){
        viewModelScope.launch {
            try{
                if (!history.contains(number)){
                    history.add(number)
                }
                val request = number.toInt()
                val result = BinApi.retrofitService.getCardData(request)
                _cardData.value = result
            }catch (e: Exception){
                Log.e("BinViewModel", e.message.toString())
            }

        }
    }
}