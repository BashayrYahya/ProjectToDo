package com.example.fragment_to_do

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.dataBase.ToDoRepository
import com.example.projecttodo.ToDoData
import java.util.*



class toDoViewModel : ViewModel(){
    private val taskRepository = ToDoRepository.get()
    private val taskIdLiveData = MutableLiveData<UUID>()



    var taskLiveData:LiveData<ToDoData?> =
        Transformations.switchMap(taskIdLiveData){
            taskRepository.getTask(it)
        }


    fun loadTask(taskeId:UUID){
        taskIdLiveData.value = taskeId
    }

    fun  updateTask (task: ToDoData){

        taskRepository.updateTask(task)
    }


}


