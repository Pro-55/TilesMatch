package com.papslabs.tilesmatch.data.repository.contract

import com.bumptech.glide.RequestManager
import com.papslabs.tilesmatch.models.Option
import com.papslabs.tilesmatch.models.Resource
import com.papslabs.tilesmatch.models.Tile
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getOptions(): Flow<Resource<List<Option>>>
    fun getGameTiles(glide: RequestManager, option: Option?): Flow<Resource<List<Tile>>>
}