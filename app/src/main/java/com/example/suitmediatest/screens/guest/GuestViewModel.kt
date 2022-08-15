package com.example.suitmediatest.screens.guest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.suitmediatest.repository.RegresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(
    private val repository: RegresRepository
): ViewModel() {

    fun getRegresList() = repository.getRegresList().cachedIn(viewModelScope)

}