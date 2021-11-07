package com.example.projecttodo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*




@Entity  // declare data class
data class ToDoData

    ( @PrimaryKey val id:UUID=UUID.randomUUID(),


      var title : String ="",
      var date: Date = Date(),
      var isDone :Boolean = false ,
      var  detailsEditTxte : String="",
      var toDate : Date = Date(),



)
