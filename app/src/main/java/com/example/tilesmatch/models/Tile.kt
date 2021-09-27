package com.example.tilesmatch.models

import android.graphics.Bitmap

data class Tile(
    val _id: Int,
    val bitmap: String? = null
){
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Tile?

        if (_id != other._id) return false
        if (bitmap != other.bitmap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id
        result = 31 * result + (bitmap?.hashCode() ?: 0)
        return result
    }
}
