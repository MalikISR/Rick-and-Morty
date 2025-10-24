package com.example.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearAll()

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters")
    suspend fun getAllCharactersList(): List<CharacterEntity>


    @Query("""
    SELECT * FROM characters 
    WHERE (:name IS NULL OR name LIKE '%' || :name || '%') 
      AND (:status IS NULL OR status = :status)
      AND (:species IS NULL OR species = :species)
      AND (:gender IS NULL OR gender = :gender)
""")
    suspend fun getFilteredCharacters(
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): List<CharacterEntity>
}
