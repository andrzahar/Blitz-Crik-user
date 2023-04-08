package com.andr.zahar2.blitzcrikuser.api

import com.andr.zahar2.blitzcrikuser.data.GameState
import com.andr.zahar2.blitzcrikuser.data.UserScore
import com.andr.zahar2.blitzcrikuser.data.question.Question
import com.andr.zahar2.blitzcrikuser.data.question.toQuestion
import com.andr.zahar2.blitzcrikuser.data.toGameState
import com.andr.zahar2.blitzcrikuser.data.toUserScore
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Api(private val client: HttpClient) {

    var host = "192.168.10.89"
    var port = 2207
    private val userPath = "/user"
    private val gamePath = "/state"
    private val questionPath = "/question"

    lateinit var name: String

    fun userScoreListener(): Flow<Float> = flow {
        client.webSocket(host = host, port = port, path = userPath) {
            send(UserScore(name, null).toString())
            incoming.consumeEach { frame ->
                if (frame !is Frame.Text) return@consumeEach
                val userScore = frame.readText().toUserScore()
                if (userScore.name == name)
                    emit(userScore.points!!)
            }
        }
    }

    fun gameStateListener(): Flow<GameState> = flow {
        client.webSocket(host = host, port = port, path = gamePath) {
            incoming.consumeEach { frame ->
                if (frame !is Frame.Text) return@consumeEach
                val gameState = frame.readText().toGameState()
                emit(gameState)
            }
        }
    }

    fun questionListener(): Flow<Question> = flow {
        client.webSocket(host = host, port = port, path = questionPath) {
            incoming.consumeEach { frame ->
                if (frame !is Frame.Text) return@consumeEach
                val question = frame.readText().toQuestion()
                emit(question)
            }
        }
    }
}