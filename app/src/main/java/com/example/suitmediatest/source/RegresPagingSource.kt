package com.example.suitmediatest.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.suitmediatest.model.DataItem
import com.example.suitmediatest.service.RegresServiceInstance
import retrofit2.HttpException
import java.io.IOException


private const val DEFAULT_INDEX_PAGE = 1
private const val DEFAULT_INDEX_SIZE = 10

class RegresPagingSource(
    private val service: RegresServiceInstance
): PagingSource<Int, DataItem>() {

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        val pageIndex = params.key ?: DEFAULT_INDEX_PAGE
        return try {
            val response = service.getListRegres(
                page = pageIndex,
                size = params.loadSize
            )
            LoadResult.Page(
                data = response.body()?.data!!,
                prevKey = if (pageIndex == DEFAULT_INDEX_PAGE) null else pageIndex.minus(1),
                nextKey = if (response.body()?.data!!.isEmpty()) null else pageIndex.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}