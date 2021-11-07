package com.example.dataBase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.projecttodo.ToDoData
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "task-database"

class ToDoRepository private constructor(context: Context){

private val  database:ToDoDataBase = Room.databaseBuilder(
    context.applicationContext,
    ToDoDataBase ::class.java,
    DATABASE_NAME
).build()


    private val toDoDao = database.taskdao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllTasks(): LiveData<List<ToDoData>> = toDoDao.getAllTasks()

    fun getTask (id: UUID): LiveData<ToDoData?> {
        return toDoDao.getTask(id)
    }

    fun updateTask (task: ToDoData){
        executor.execute {
            toDoDao.updateTasks(task)
        }

    }


    fun addTasks(task : ToDoData){
        executor.execute {
            toDoDao.addTasks(task)

        }
    }

    fun deleteTasks(task : ToDoData) {
        executor.execute {
            toDoDao.deleteTasks(task)
        }
    }
    companion object{
        private  var INSTANCE:ToDoRepository? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = ToDoRepository(context)
            }

        }

        fun get() :ToDoRepository{
            return INSTANCE ?:
            throw IllegalStateException("")
        }
    }
}




