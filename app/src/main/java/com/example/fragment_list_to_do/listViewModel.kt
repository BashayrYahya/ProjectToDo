package com.example.fragment_list_to_do

import androidx.lifecycle.ViewModel
import com.example.dataBase.ToDoRepository
import com.example.projecttodo.ToDoData

class listViewModel : ViewModel() {

    val toDoRepository = ToDoRepository.get()
    val liveDataTask = toDoRepository.getAllTasks()  // وين استخدمها

    fun addTasks(task: ToDoData) {
        toDoRepository.addTasks(task)
    }

    fun deleteTasks(task: ToDoData) {
        toDoRepository.deleteTasks(task)
    }

}