package de.task.DB

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val allTasks: List<Task> = repository.allTask

    val getUser: User= repository.getUser

    val allCompletedTask: List<CompletedTask> = repository.allCompletedTask

    val firstTask: String = repository.firstTask

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun insert(completedTask: CompletedTask) = viewModelScope.launch {
        repository.insert(completedTask)
    }

}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}