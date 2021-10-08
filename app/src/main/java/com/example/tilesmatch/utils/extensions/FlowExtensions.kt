package com.example.tilesmatch.utils.extensions

import android.util.Log
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * wraps passed block in flow and converts it into resource flow
 *
 * @param block block of code to be executed
 * @return regular flow converted into resource flow
 */
fun <T> resourceFlow(
    block: suspend FlowCollector<Resource<T>>.() -> Unit
): Flow<Resource<T>> = flow(block).asResourceFlow()

/**
 * converts regular flow into resource flow
 * @return resource flow for given flow
 */
fun <T> Flow<Resource<T>>.asResourceFlow(): Flow<Resource<T>> =
    onStart { emit(Resource.loading(data = null)) }
        .distinctUntilChanged()
        .catch {
            it.printStackTrace()
            when (it) {
                is IllegalArgumentException -> Log.d("RF", "TestLog: IllegalArgumentException")
                else -> Log.d("RF", "TestLog: Exception")
            }
            emit(Resource.error(msg = Constants.MSG_SOMETHING_WENT_WRONG, data = null))
        }
        .flowOn(Dispatchers.IO)