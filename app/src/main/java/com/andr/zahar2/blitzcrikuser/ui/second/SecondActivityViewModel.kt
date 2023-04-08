package com.andr.zahar2.blitzcrikuser.ui.second

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
class SecondActivityViewModel @Inject constructor(private val api: Api): ViewModel() {

    val name = api.name

    private val _statePoints = mutableStateOf("")
    val statePoints: State<String> = _statePoints

    private val _stateShowScreen = mutableStateOf(true)
    val stateShowScreen: State<Boolean> = _stateShowScreen

    init {
        api.userScoreListener().onEach {
            val d2 = it % 1
            val res = if (d2 == 0f) {
                (it - d2).toInt().toString()
            } else {
                it.toString().replace('.', ',')
            }
            _statePoints.value = res
        }.launchIn(viewModelScope)

        api.gameStateListener().onEach {
            _stateShowScreen.value = it != GameState.GAME &&  it != GameState.WINNER
        }.launchIn(viewModelScope)
    }
}