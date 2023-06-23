package com.jetpackcompose.chatapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackcompose.chatapplication.ui.theme.ChatApplicationTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatApplicationTheme {
                ChatScreen()
            }
        }
    }
}

@Composable
fun ChatScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), verticalArrangement = Arrangement.SpaceBetween
    ) {
        ChatSection()
    }
}

@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatSection() {

    val lazyColumnListState = rememberLazyListState()
    val corroutineScope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }
    val simpleDateTime = SimpleDateFormat("h:mm: a", Locale.ENGLISH)
    val chatList = remember { mutableStateListOf<ChatModel>() }
    chatList.addAll(message_dummy)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Nayeem Shiddiki Abir \nOnline", modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray)
                .padding(10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(8f)
                .padding(16.dp), reverseLayout = true, state = lazyColumnListState
        ) {
            items(chatList) { chatModel ->
                MessageItem(
                    messageText = chatModel.text,
                    time = chatModel.time,
                    isBot = chatModel.isBot!!
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                placeholder = {
                    Text("Write message here....")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                trailingIcon = {
                    if (message.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Send,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.clickable {
                                chatList.add(0,
                                    ChatModel(
                                        message,
                                        "1001",
                                        simpleDateTime.format(Calendar.getInstance().timeInMillis),
                                        false
                                    )
                                )
                                /** -- Scroll to last added items of list -- **/
                                corroutineScope.launch {
                                    lazyColumnListState.animateScrollToItem(chatList.size - 1)
                                }
                            }
                        )
                    }
                }
            )
        }
    }


}

@Composable
fun MessageItem(messageText: String, time: String, isBot: Boolean) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = if (isBot) Alignment.Start else Alignment.End
    ) {
        if (messageText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .background(
                        if (isBot) Color.Magenta else Color.Black,
                        shape = if (isBot) RoundedCornerShape(
                            0.dp,
                            8.dp,
                            8.dp,
                            8.dp
                        ) else RoundedCornerShape(
                            0.dp, 8.dp, 8.dp, 8.dp
                        )
                    )
                    .padding(16.dp, 8.dp, 16.dp, 8.dp)
            ) {
                Text(text = messageText, color = Color.White)
            }
        }
        Text(text = time, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
    }
}
