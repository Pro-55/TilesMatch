package com.example.tilesmatch.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val _id: Int,
    val title: String,
    val url: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Option?

        if (_id != other._id) return false
        if (title != other.title) return false
        return url == other.url
    }

    override fun hashCode(): Int {
        var result = _id
        result = 31 * result + title.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}