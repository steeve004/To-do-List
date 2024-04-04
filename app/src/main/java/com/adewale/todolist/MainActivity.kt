package com.adewale.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerApp()
        }
    }
}

data class Task(
    val id: Int,
    var title: String,
    var priority: Priority,
    var dueDate: Date?
)

enum class Priority { HIGH, MEDIUM, LOW }

@Composable
fun TaskManagerApp() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskPriority by remember { mutableStateOf(Priority.MEDIUM) }
    var newTaskDueDate by remember { mutableStateOf<Date?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = newTaskTitle,
            onValueChange = { newTaskTitle = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("Priority: ")
            Spacer(modifier = Modifier.width(8.dp))
            PriorityDropdown(
                priority = newTaskPriority,
                onPrioritySelected = { newTaskPriority = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        DatePicker(
            selectedDate = newTaskDueDate,
            onDateSelected = { newTaskDueDate = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                tasks = tasks.toMutableList().apply {
                    val newTaskId = if (tasks.isEmpty()) 0 else tasks.maxByOrNull { it.id }!!.id + 1
                    add(Task(newTaskId, newTaskTitle, newTaskPriority, newTaskDueDate))
                }
                newTaskTitle = ""
                newTaskPriority = Priority.MEDIUM
                newTaskDueDate = null
            },
            modifier = Modifier.align(Alignment.End)

        ) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task)
            }
        }
    }
}

@Composable
fun PriorityDropdown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    DropdownMenu(
        expanded = false, // Change to true to see the dropdown
        onDismissRequest = { /* Dismiss the dropdown */ }
    ) {
        Priority.values().forEach { priority ->
            DropdownMenuItem(onClick = { onPrioritySelected(priority) }) {
                Text(priority.name)
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

@Composable
fun TaskItem(task: Task) {
    // Display task item
}

@Composable
fun DatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit
) {
    // Display date picker
}
