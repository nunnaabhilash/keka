package com.example.todoapp

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.todolistapp.R
import java.time.LocalDate
import java.util.*

class TaskItem(
    var name: String,
    var desc: String,
    var priority: String,
    var dueDate: LocalDate?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
)
{
    fun isCompleted() = completedDate != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}