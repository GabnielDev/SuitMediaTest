package com.example.suitmediatest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.suitmediatest.model.DataItem
import com.example.suitmediatest.service.RegresServiceInstance
import com.example.suitmediatest.source.RegresPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegresRepository @Inject constructor(
    private val service: RegresServiceInstance
){

    fun getRegresList(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 50
            ),
            pagingSourceFactory = {
                RegresPagingSource(service = service)
            }
        ).flow
    }

}