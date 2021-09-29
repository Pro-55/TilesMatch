package com.example.tilesmatch.utils.extensions

import android.util.Log
import com.example.tilesmatch.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <T> resourceFlow(
    block: suspend FlowCollector<Resource<T>>.() -> Unit
): Flow<Resource<T>> = flow(block).asResourceFlow()

fun <T> Flow<Resource<T>>.asResourceFlow(): Flow<Resource<T>> =
    onStart { emit(Resource.loading(data = null)) }
        .distinctUntilChanged()
        .catch {
            it.printStackTrace()
            when (it) {
                is IllegalArgumentException -> Log.d("RF", "TestLog: IllegalArgumentException")
                else -> Log.d("RF", "TestLog: Exception")
            }
            emit(Resource.error(msg = "Something went wrong!", data = null))
        }
        .flowOn(Dispatchers.IO)