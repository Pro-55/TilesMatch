package com.example.tilesmatch.models

import android.graphics.Bitmap

data class Tile(
    val _id: Int,
    val bitmap: Bitmap? = null,
    val shouldShowId: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Tile?

        if (_id != other._id) return false
        if (bitmap != other.bitmap) return false
        if (shouldShowId != other.shouldShowId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id
        result = 31 * result + (bitmap?.hashCode() ?: 0)
        result = 31 * result + shouldShowId.hashCode()
        return result
    }
}