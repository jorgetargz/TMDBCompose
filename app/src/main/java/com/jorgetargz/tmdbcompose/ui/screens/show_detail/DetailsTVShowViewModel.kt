package com.jorgetargz.tmdbcompose.ui.screens.show_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.tmdbcompose.domain.models.TVShow
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadCachedTVShowByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.tv_shows.LoadTVShowByIdUseCase
import com.jorgetargz.tmdbcompose.ui.common.Constants
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import com.jorgetargz.tmdbcompose.utils.hasInternetConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import timber.log.Timber

class DetailsTVShowViewModel(
    private val appContext: Context,
    private val loadTVShowByIdUseCase: LoadTVShowByIdUseCase,
    private val loadCachedTVShowByIdUseCase: LoadCachedTVShowByIdUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsTVShowContract.DetailsTVShowState> by lazy {
        MutableStateFlow(DetailsTVShowContract.DetailsTVShowState())
    }
    val uiState: StateFlow<DetailsTVShowContract.DetailsTVShowState> = _uiState

    private fun loadTVShowById(id: Int) {
        viewModelScope.launch {
            if (appContext.hasInternetConnection()) {
                loadTVShowByIdUseCase.invoke(id)
                    .catch(action = { cause ->
                        _uiState.update {
                            it.copy(
                                error = cause.message,
                                isLoading = false
                            )
                        }
                        Timber.e(cause)
                    })
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false
                                    )
                                }
                                Timber.e(result.message)
                            }
                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(isLoading = true)
                            }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    tvShow = result.data ?: TVShow(),
                                    isLoading = false
                                )
                            }
                        }
                    }
            } else {
                loadCachedTVShowByIdUseCase.invoke(id)
                    .catch(action = { cause ->
                        _uiState.update {
                            it.copy(
                                error = cause.message,
                                isLoading = false
                            )
                        }
                        Timber.e(cause)
                    })
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false
                                    )
                                }
                                Timber.e(result.message)
                            }
                            is NetworkResult.Loading -> _uiState.update {
                                it.copy(isLoading = true)
                            }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    error = Constants.CACHE_LOADED,
                                    tvShow = result.data ?: TVShow(),
                                    isLoading = false
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }


    fun handleEvent(event: DetailsTVShowContract.DetailsTVShowEvent) {
        when (event) {
            is DetailsTVShowContract.DetailsTVShowEvent.LoadTVShow -> loadTVShowById(event.id)
            DetailsTVShowContract.DetailsTVShowEvent.ClearError -> clearError()
        }
    }
}