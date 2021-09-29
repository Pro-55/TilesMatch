package com.example.tilesmatch.data.viewmodel

import androidx.lifecycle.*
import com.bumptech.glide.RequestManager
import com.example.tilesmatch.data.repository.contract.Repository
import com.example.tilesmatch.models.Option
import com.example.tilesmatch.models.Resource
import com.example.tilesmatch.models.Tile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    // Global
    private val TAG = MainViewModel::class.java.simpleName
    private val _options = MutableLiveData<Resource<List<Option>>>()
    val options: LiveData<Resource<List<Option>>> = _options
    private val _tiles = MutableLiveData<Resource<List<Tile>>>()
    val tiles: LiveData<Resource<List<Tile>>> = _tiles

    fun getOptions() {
        repository.getOptions()
            .onEach { resource -> _options.postValue(resource) }
            .launchIn(viewModelScope)
    }

    fun getGameTiles(
        glide: RequestManager,
        option: Option
    ) {
        repository.getGameTiles(glide, option)
            .onEach { resource ->                _tiles.postValue(resource)
            }
            .launchIn(viewModelScope)
    }

    fun clearGameTiles() {
        _tiles.postValue(Resource.loading())
    }
}