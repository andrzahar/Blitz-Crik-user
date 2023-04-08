package com.andr.zahar2.blitzcrikuser.ui.main

import androidx.lifecycle.ViewModel
import com.andr.zahar2.blitzcrikuser.api.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val api: Api): ViewModel() {

    val host = api.host
    val port = api.port

    fun onButtonClick(name: String, host: String, port: String, after: () -> Unit) {
        api.name = name
        api.host = host
        api.port = port.toInt()
        after()
    }
}