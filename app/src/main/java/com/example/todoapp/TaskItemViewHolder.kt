package com.example.todoapp

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root)
{
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun bindTaskItem(taskItem: TaskItem)
    {
        binding.name.text = taskItem.name
        binding.priority.text = taskItem.priority
        binding.status.text = "Inprogress"

        if (taskItem.isCompleted()){
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.priority.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.status.text = "Completed"
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener{
            clickListener.completeTaskItem(taskItem)
        }
        binding.taskCellContainer.setOnClickListener{
            clickListener.editTaskItem(taskItem)
        }

        if(taskItem.dueDate != null)
            binding.dueDate.text = dateFormat.format(taskItem.dueDate)
        else
            binding.dueDate.text = ""
    }
}