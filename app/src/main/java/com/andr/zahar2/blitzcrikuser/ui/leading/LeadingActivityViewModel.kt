package com.andr.zahar2.blitzcrikuser.ui.leading

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andr.zahar2.blitzcrikuser.api.Api
import com.andr.zahar2.blitzcrikuser.data.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LeadingActivityViewModel @Inject constructor(private val api: Api): ViewModel() {

    private val _stateRound = mutableStateOf("")
    val stateRound: State<String> = _stateRound

    init {
        api.questionListener().onEach {
            _stateRound.value = it.roundName
        }.launchIn(viewModelScope)
    }
}