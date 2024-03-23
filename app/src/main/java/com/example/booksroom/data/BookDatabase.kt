package com.example.booksroom.data

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract val bookDao: BookDao

    companion object{
        @Volatile
        private var INSTANCE: BookDatabase? = null
        fun getDatabase(context: Context): BookDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(context,BookDatabase::class.java,"book_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it

                }
            }
        }

    }
}