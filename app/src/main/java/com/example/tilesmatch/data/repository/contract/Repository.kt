package com.example.tilesmatch.data.repository.contract

import com.bumptech.glide.RequestManager
import com.example.tilesmatch.models.Option
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.models.Tile
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getOptions(): Flow<Resource<List<Option>>>
    fun getGameTiles(glide: RequestManager, option: Option): Flow<Resource<List<Tile>>>
}