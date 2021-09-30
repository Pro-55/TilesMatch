package com.example.tilesmatch.utils.extensions

import android.content.res.AssetManager
import java.io.IOException

/**
 * read file from given path
 *
 * @param path oath of the json file
 * @return stringified json
 */
@Throws(IOException::class)
fun AssetManager.readFile(path: String): String {
    val stream = open(path)
    val size = stream.available()
    val buffer = ByteArray(size)
    stream.read(buffer)
    stream.close()
    return String(buffer)
}