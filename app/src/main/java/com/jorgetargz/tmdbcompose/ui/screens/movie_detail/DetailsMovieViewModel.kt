package com.jorgetargz.tmdbcompose.ui.screens.movie_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadCachedMovieByIdUseCase
import com.jorgetargz.tmdbcompose.domain.use_cases.movies.LoadMovieByIdUseCase
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import com.jorgetargz.tmdbcompose.utils.hasInternetConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsMovieViewModel(
    private val appContext: Context,
    private val loadMovieByIdUseCase: LoadMovieByIdUseCase,
    private val loadCachedMovieByIdUseCase: LoadCachedMovieByIdUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsMovieContract.DetailsMovieState> by lazy {
        MutableStateFlow(DetailsMovieContract.DetailsMovieState())
    }
    val uiState: StateFlow<DetailsMovieContract.DetailsMovieState> = _uiState

    private fun loadMovieById(id: Int) {
        viewModelScope.launch {
            if (appContext.hasInternetConnection()) {
                loadMovieByIdUseCase.invoke(id)
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
                                    movie = result.data ?: Movie(),
                                    isLoading = false
                                )
                            }
                        }
                    }
            } else {
                loadCachedMovieByIdUseCase.invoke(id)
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
                                    error = "Loaded from cache",
                                    movie = result.data ?: Movie(),
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
            it.copy(
                error = null
            )
        }
    }


    fun handleEvent(event: DetailsMovieContract.DetailsMovieEvent) {
        when (event) {
            is DetailsMovieContract.DetailsMovieEvent.LoadMovie -> loadMovieById(event.id)
            DetailsMovieContract.DetailsMovieEvent.ClearError -> clearError()
        }
    }
}