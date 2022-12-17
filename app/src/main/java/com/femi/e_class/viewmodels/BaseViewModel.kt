package com.femi.e_class.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.femi.e_class.repositories.BaseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val repository: BaseRepository,
) : ViewModel() {

    val useDynamicTheme = true

    fun userEmail(email: String) = viewModelScope.launch {
        repository.userEmail(email)
    }

    suspend fun userEmail(): String = repository.userEmail()

    fun userMatric(matric: Long) = viewModelScope.launch {
        repository.userMatric(matric)
    }

    suspend fun userMatric(): Long = repository.userMatric()

    fun userFName(fName: String) = viewModelScope.launch {
        repository.userFName(fName)
    }

    suspend fun userFName(): String = repository.userFName()

    fun userLName(lName: String) = viewModelScope.launch {
        repository.userLName(lName)
    }

    suspend fun userLName(): String = repository.userLName()

    open fun logOut(){
        viewModelScope.launch {
            userFName("")
            userLName("")
            userMatric(0L)
            userEmail("")
        }
    }

    private val classStatusChannel = Channel<ClassStatus<Any?>>()
    val classStatus = classStatusChannel.receiveAsFlow()

    fun classStarted(){
        viewModelScope.launch {
            classStatusChannel.send(ClassStatus.Started)
        }
    }

    fun classEnded(){
        viewModelScope.launch {
            classStatusChannel.send(ClassStatus.Ended)
        }
    }

    sealed class ClassStatus<out T> {
        object Started : ClassStatus<Nothing>()
        object Ended : ClassStatus<Nothing>()
    }
}
