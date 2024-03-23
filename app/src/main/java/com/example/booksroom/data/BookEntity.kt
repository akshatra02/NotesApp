package com.example.booksroom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookEntity(
    @ColumnInfo(name = "book_title") val bookTitle: String,
    @ColumnInfo(name = "book_author") val bookAuthor: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")val bookId: Int? =null,
)

