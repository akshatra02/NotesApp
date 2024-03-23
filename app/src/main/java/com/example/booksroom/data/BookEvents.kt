package com.example.booksroom.data

sealed interface BookEvents {
    data class deleteBook(val book : BookEntity) : BookEvents
    object sortBook : BookEvents
    data class SaveBook(val title: String, val author: String) : BookEvents
}