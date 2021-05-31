package com.android.rickandmortyproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.rickandmortyproject.database.dao.CharacterDao
import com.android.rickandmortyproject.database.entity.CharacterDto
import com.android.rickandmortyproject.database.entity.FavoriteCharacterDto

@Database(
    entities = [CharacterDto::class, FavoriteCharacterDto::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}