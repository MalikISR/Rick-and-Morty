package com.example.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.util.NetworkUtils
import com.example.data.api.RickAndMortyApi
import com.example.data.local.CharacterDao
import com.example.data.local.CharacterEntity
import com.example.data.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao,
    private val context: Context
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                val lastPage = (lastItem.id / state.config.pageSize)
                lastPage + 1
            }
        }

        return try {
            val response = api.getCharacters(page)
            val entities = response.results.map { it.toEntity() }

            if (loadType == LoadType.REFRESH) {
                dao.clearAll()
            }

            dao.insertAll(entities)
            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
