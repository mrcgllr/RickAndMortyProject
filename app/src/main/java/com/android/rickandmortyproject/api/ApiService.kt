package com.android.rickandmortyproject.api

import com.android.rickandmortyproject.model.character.CharacterResponse
import com.android.rickandmortyproject.model.episode.EpisodeItem
import com.android.rickandmortyproject.util.Constants.END_POINT_ALL_CHARACTER
import com.android.rickandmortyproject.util.Constants.END_POINT_EPISODE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(END_POINT_ALL_CHARACTER)
    suspend fun getCharacters(@Query("page") page: Int,
                              @Query("name") name: String,
                              @Query("status") status: String): CharacterResponse

    @GET(END_POINT_EPISODE)
    suspend fun getDetailEpisode(@Path("id") episode: Int): EpisodeItem
}