package com.example.todolistapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import android.widget.Toast
import java.util.*

class AddTask : AppCompatActivity() {
    val myCalendar = Calendar.getInstance()
    var myFormat = "MM/dd/yy"
    var dateFormat = SimpleDateFormat(myFormat)
    var color = "blue"
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        var editDate = findViewById<EditText>(R.id.date)
        var editTime = findViewById<EditText>(R.id.time)
        var date = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,day)
            editDate.setText(dateFormat.format(myCalendar.getTime()))
        }

        editDate.setOnClickListener {
            val dateDialog = DatePickerDialog(
                this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.setTitle("Select Date")
            dateDialog.show()
        }

        editTime.setOnClickListener {
            var currTime = Calendar.getInstance()
            var hour = currTime.get(Calendar.HOUR_OF_DAY)
            var minute = currTime.get(Calendar.MINUTE)
            val timeDialog = TimePickerDialog (this,
                TimePickerDialog.OnTimeSetListener{ timePicker, selectedHour, selectedMinute ->
                    editTime.setText("$selectedHour : $selectedMinute") },
                hour, minute, true)

            timeDialog.setTitle("Select Time")
            timeDialog.show()
        }

    }

    fun chooseColor(v : View){

        var textView = findViewById<TextView>(R.id.color)

        when (v.getId()){
            R.id.button_blue -> {
                textView.setText("Blue")
                color = "blue"
            }
            R.id.button_red -> {
                textView.setText("Red")
                color = "red"
            }
            R.id.button_green -> {
                textView.setText("Green")
                color = "green"
            }
            R.id.button_yellow -> {
                textView.setText("Yellow")
                color = "yellow"
            }

        }
    }

    fun addTask (v : View){
        var taskName = findViewById<EditText>(R.id.taskName)
        var editDate = findViewById<EditText>(R.id.date)
        var editTime = findViewById<EditText>(R.id.time)

        database=FirebaseDatabase.getInstance().reference
        val todoItemData = ToDoModel.createList()
        todoItemData.itemDataText=taskName.text.toString()
        todoItemData.dateDb=editDate.text.toString()
        todoItemData.timeDb=editTime.text.toString()
        todoItemData.colorDb=color
        todoItemData.done=false

        val newItemData = database.child("todo").push()
        todoItemData.UID = newItemData.key

        newItemData.setValue(todoItemData)

        Toast.makeText(this, "item saved", Toast.LENGTH_LONG).show()


    }
}