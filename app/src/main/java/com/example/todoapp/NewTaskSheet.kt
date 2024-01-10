package com.example.todoapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueDate: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null)
        {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueDate != null){
                dueDate = taskItem!!.dueDate!!
                updateDateButtonText()
            }
        }
        else
        {
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
        val priorityOptions = arrayOf("Low", "Medium", "High")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = spinnerAdapter
        if (taskItem != null) {
            // Set existing priority if editing
            val priorityIndex = priorityOptions.indexOf(taskItem!!.priority)
            if (priorityIndex != -1) {
                binding.prioritySpinner.setSelection(priorityIndex)
            }
        }
    }
//        val prioritySpinner: Spinner = view.findViewById(R.id.prioritySpinner)
//        val priorityOptions = arrayOf("Low", "Medium", "High")
//        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityOptions)
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        prioritySpinner.adapter = spinnerAdapter


    private fun openTimePicker() {
        if(dueDate == null)
            dueDate = LocalDate.now()
        var listener = DatePickerDialog.OnDateSetListener{ _, a, b, c ->
            dueDate = LocalDate.of(a, b, c)
            updateDateButtonText()
        }

        val dialog = DatePickerDialog(requireContext(), listener, dueDate!!.year, dueDate!!.monthValue, dueDate!!.dayOfMonth)
        dialog.setTitle("Task Due")
        dialog.show()

    }

    private fun updateDateButtonText() {
        binding.timePickerButton.text = String.format("%04d-%02d-%02d",dueDate!!.year,dueDate!!.monthValue, dueDate!!.dayOfMonth)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    private fun saveAction()
    {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val priority = binding.prioritySpinner.selectedItem.toString()

        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,priority,dueDate,null)
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskViewModel.updateTaskItem(taskItem!!.id, name, desc, priority,  dueDate)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

}








