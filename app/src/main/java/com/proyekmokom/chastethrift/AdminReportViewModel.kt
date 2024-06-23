package com.proyekmokom.chastethrift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyekmokom.chastethrift.Network.ApiConfig
import kotlinx.coroutines.launch

class AdminReportViewModel() : ViewModel() {

    private val _ipAddress = MutableLiveData<String>().apply {
        value = "finding your IP Address..."
    }
    val ipAddress: LiveData<String> = _ipAddress

    fun fetchIpAddress () {
        _ipAddress.value = "finding your IP Address..."

        viewModelScope.launch {
            val api = ApiConfig.getApiService()
            val response = api.getIp()
            if (response.isSuccessful) {
                _ipAddress.value = response.body()!!.ip
            }
        }
    }
}