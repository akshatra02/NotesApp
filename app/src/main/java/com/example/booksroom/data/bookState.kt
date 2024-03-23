package com.example.booksroom.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class bookState(
    val books:List<BookEntity> = emptyList(),
    val bookTitle:MutableState<String> = mutableStateOf(""),
    val bookAuthor:MutableState<String> = mutableStateOf(""),
)
