package com.example.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projecttodo.ToDoData
import java.util.*


@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoData")
    fun getAllTasks (): LiveData<List<ToDoData>>

    @Query("SELECT * FROM ToDoData WHERE id= (:id)")
    fun getTask(id: UUID): LiveData<ToDoData?>

    @Update
    fun updateTasks (task: ToDoData)

    @Insert
    fun addTasks(task : ToDoData)

    @Delete
    fun deleteTasks(task : ToDoData)



}