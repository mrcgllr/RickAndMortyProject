package com.android.rickandmortyproject.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.rickandmortyproject.api.ApiService
import com.android.rickandmortyproject.database.AppDatabase
import com.android.rickandmortyproject.database.dao.CharacterDao
import com.android.rickandmortyproject.database.entity.CharacterDto
import java.io.InvalidObjectException


@OptIn(ExperimentalPagingApi::class)
class CharacterMediator(
    private val network: ApiService,
    private val database: AppDatabase,
    private val dao: CharacterDao,
    private val name: String,
    private val status: String
) : RemoteMediator<Int, CharacterDto>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDto>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                dao.clearAll()
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val page = getRemoteKeyForLastItem(state)
                    ?: throw InvalidObjectException("not null")
                page.plus(1)
            }
        }
        try {
            val response = network.getCharacters(page = loadKey, name = name, status = status)
            val list = response.results
            val endOfPaginationReached = list.isEmpty()

            database.withTransaction {
                val nextKey = if (endOfPaginationReached) null else loadKey
                nextKey?.let { key ->
                    val dtoList: List<CharacterDto> = list.map {
                        CharacterDto.ModelMapper.fromCharacter(key, it)
                    }
                    dao.insertAll(dtoList)
                }

                val favItem = dao.loadAllFavorite()
                favItem.forEach { item ->
                    dao.updateCharacterDtoFavorite(item.id, true)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterDto>): Int? {
        if (state.anchorPosition == null) return 1
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { item ->
            dao.loadPage(item.databaseId)
        }
    }
}