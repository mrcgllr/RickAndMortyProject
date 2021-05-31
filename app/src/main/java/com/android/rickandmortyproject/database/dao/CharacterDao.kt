package com.android.rickandmortyproject.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.database.entity.FavoriteCharacterDto

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dtoList: List<CharacterDto>)

    @Query("SELECT * FROM character_dto")
    fun loadAllItems(): PagingSource<Int, CharacterDto>

    @Query("SELECT * FROM character_dto")
    suspend fun loadAllList(): MutableList<CharacterDto>

    @Query("SELECT page FROM character_dto WHERE databaseId=:id LIMIT 1")
    suspend fun loadPage(id: Long): Int

    @Query("DELETE FROM character_dto")
    suspend fun clearAll()

    @Query("SELECT * FROM character_dto WHERE id=:id LIMIT 1")
    suspend fun getSingleCharacter(id: Int): CharacterDto

    @Query("UPDATE character_dto SET viewType = :viewType WHERE id= :id")
    suspend fun updateViewType(id:Int,viewType: Int)

    @Query("UPDATE character_dto SET isFavorite = :isFavorite WHERE id= :id")
    suspend fun updateCharacterDtoFavorite(id: Int, isFavorite: Boolean)

    // Favorite Dto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dto: FavoriteCharacterDto)

    @Query("SELECT * FROM favorite_character_dto")
    suspend fun loadAllFavorite(): List<FavoriteCharacterDto>

    @Query("DELETE FROM favorite_character_dto WHERE id=:id")
    suspend fun deleteFavItem(id:Int)
}