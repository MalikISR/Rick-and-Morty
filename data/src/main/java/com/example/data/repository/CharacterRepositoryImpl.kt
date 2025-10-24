package com.example.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.model.Character
import com.example.data.api.RickAndMortyApi
import com.example.data.local.CharacterDao
import com.example.data.local.CharacterEntity
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao,
    private val context: Context
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersPaged(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = CharacterRemoteMediator(api, dao, context),
            pagingSourceFactory = { dao.getAllCharacters() }
        ).flow.map { pagingData ->
            pagingData.map(CharacterEntity::toDomain)
        }
    }

    override suspend fun getCharacterById(id: Int): Character? {
        val local = dao.getCharacterById(id)
        return if (local != null) {
            local.toDomain()
        } else {
            try {
                val remote = api.getCharacterDetails(id)
                val entity = remote.toEntity()
                dao.insertAll(listOf(entity))
                entity.toDomain()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getFilteredCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): List<Character> {
        return try {
            val response = api.getCharacters(
                page = 1,
                name = name,
                status = status,
                species = species,
                gender = gender
            )
            val entities = response.results.map { it.toEntity() }

            dao.insertAll(entities)

            entities.map { it.toDomain() }
        } catch (e: Exception) {
            dao.getFilteredCharacters(name, status, species, gender)
                .map { it.toDomain() }
        }
    }
}
