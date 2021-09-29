package com.example.tilesmatch.data.repository.impl

import android.content.res.AssetManager
import android.graphics.Bitmap
import com.bumptech.glide.RequestManager
import com.example.tilesmatch.R
import com.example.tilesmatch.data.repository.contract.Repository
import com.example.tilesmatch.models.Option
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.models.Tile
import com.example.tilesmatch.utils.extensions.readFile
import com.example.tilesmatch.utils.extensions.resourceFlow
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random


class RepositoryImpl @Inject constructor(
    private val am: AssetManager
) : Repository {

    // Global
    private val TAG = RepositoryImpl::class.java.simpleName
    private val gson = GsonBuilder().create()

    override fun getOptions(): Flow<Resource<List<Option>>> = resourceFlow {
        val json = am.readFile("data.json")
        val data =
            gson.fromJson<List<Option>>(json, object : TypeToken<List<Option>>() {}.type)
        if (data.isNullOrEmpty()) emit(Resource.error("No data found!"))
        else emit(Resource.success(data))
    }

    override fun getGameTiles(
        glide: RequestManager,
        option: Option
    ): Flow<Resource<List<Tile>>> = resourceFlow {
        val url = when (option._id) {
            0 -> R.drawable.slytherin
            1 -> R.drawable.gryffindor
            2 -> R.drawable.hufflepuff
            3 -> R.drawable.ravenclaw
            else -> option.url
        }
        val bitmap = glide.asBitmap()
            .load(url)
            .submit()
            .get()!!

        val row = bitmap.width / 4
        val column = bitmap.height / 4
        val list = mutableListOf<Tile>()
        for (i in 0..3) {
            for (j in 0..3) {
                val b = if (i != 3 || j != 3) {
                    val startX = column * j
                    val startY = row * i
                    Bitmap.createBitmap(bitmap, startX, startY, column, row)
                } else null
                list.add(Tile(_id = (i * 4) + j, bitmap = b))
            }
        }
        emit(Resource.success(getShuffledList(list)))
    }

    private fun getShuffledList(list: List<Tile>): List<Tile> {
        val shuffledList = mutableListOf<Tile>()
        do {
            shuffledList.clear()
            shuffledList.addAll(list)
            shuffle(shuffledList)
        } while (!isSolvable(shuffledList))
        return shuffledList
    }

    private fun shuffle(tiles: MutableList<Tile>) {
        var n = tiles.size - 1
        while (n > 1) {
            val r = Random.nextInt(n--)
            val temp = tiles[r].copy()
            tiles[r] = tiles[n].copy()
            tiles[n] = temp
        }
    }

    private fun isSolvable(tiles: List<Tile>): Boolean {
        var countInversions = 0
        val n = tiles.size - 1
        for(i in 0 until n){
            for(j in 0 until i){
                if(tiles[j]._id > tiles[i]._id) ++countInversions
            }
        }
        return countInversions % 2 == 0
    }
}