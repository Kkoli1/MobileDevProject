package com.example.todolistapp

class ToDoModel {
    companion object Factory{
        fun createList(): ToDoModel = ToDoModel()
    }
    var UID: String? = null
    var itemDataText: String? = null
    var dateDb: String? = null
    var timeDb: String? = null
    var colorDb: String? = null
    var done: Boolean? = false
}