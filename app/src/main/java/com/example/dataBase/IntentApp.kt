package com.example.dataBase

import android.app.Application


class IntentApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ToDoRepository.initialize(this)
    }
}