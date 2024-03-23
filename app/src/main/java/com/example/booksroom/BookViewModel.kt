package com.example.booksroom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksroom.data.BookDao
import com.example.booksroom.data.BookEntity
import com.example.booksroom.data.BookEvents
import com.example.booksroom.data.bookState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookViewModel(private val dao: BookDao): ViewModel(){
    private val booksortedbyid = MutableStateFlow(true)
    private var books =
        booksortedbyid.flatMapLatest {
            sort -> dao.getBooks()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val _state = MutableStateFlow(bookState())
    val state  = combine(
        _state,booksortedbyid,books){ state,bookstoredbyid,books ->
            state.copy(
                books = books
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), bookState())
    fun onEvent(event: BookEvents){
        when(event){
            is BookEvents.deleteBook -> {
                viewModelScope.launch {
                    dao.deleteBook(event.book)
                }
            }
            is BookEvents.SaveBook -> {
                val book = BookEntity(
                        bookTitle = state.value.bookTitle.value,
                        bookAuthor = state.value.bookAuthor.value,
                    )
                viewModelScope.launch {
                    dao.addBook(book)
                }
                _state.update {
                    it.copy(
                        bookTitle = mutableStateOf(""),
                        bookAuthor = mutableStateOf(""),
                    )
                }
            }
            BookEvents.sortBook -> {
                booksortedbyid
            }
        }
    }
}