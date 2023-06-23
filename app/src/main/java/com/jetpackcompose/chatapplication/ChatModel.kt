package com.jetpackcompose.chatapplication

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


val simpleDateTime = SimpleDateFormat("h:mm: a", Locale.ENGLISH)

data class ChatModel(
    var text: String = "",
    var userId: String = "",
    var time: String = simpleDateTime.format(Calendar.getInstance().timeInMillis),
    val isBot: Boolean? = null
)
