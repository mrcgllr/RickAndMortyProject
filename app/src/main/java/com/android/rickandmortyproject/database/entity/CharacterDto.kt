package com.android.rickandmortyproject.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.rickandmortyproject.model.character.Result
import com.android.rickandmortyproject.util.Constants.TRIM_EPISODE_URL
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "character_dto")
data class CharacterDto(
    @PrimaryKey(autoGenerate = true) val databaseId: Long = 0,
    val page: Int,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val status: String,
    val species: String,
    val episodesCount: Int,
    val lastLocation: String,
    val gender: String,
    val characterLastEpisode: String,
    val isFavorite: Boolean,
    val viewType: Int = 0
) : Parcelable {

    object ModelMapper {

        fun fromCharacter(page: Int, character: Result): CharacterDto = CharacterDto(
            page = page,
            id = character.id,
            imageUrl = character.image,
            name = character.name,
            status = character.status,
            species = character.species,
            episodesCount = character.episode.size,
            lastLocation = character.location.name,
            gender = character.gender,
            characterLastEpisode = character.episode.last().replace(TRIM_EPISODE_URL, ""),
            isFavorite = false
        )
    }

}