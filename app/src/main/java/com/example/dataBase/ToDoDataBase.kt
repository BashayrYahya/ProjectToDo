package com.example.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.projecttodo.ToDoData


@Database( entities = [ToDoData::class],version = 1)
@TypeConverters (ToDoTypeConverters::class) // to make it primitive data

abstract class ToDoDataBase  :RoomDatabase (){
    abstract fun taskdao(): ToDoDao


}
