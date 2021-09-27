package com.example.tilesmatch.models

data class Option(
    val _id: String,
    val url: String?
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Option?

        if (_id != other._id) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }
}