package com.jorgetargz.tmdbcompose.data


import com.jorgetargz.tmdbcompose.data.local.MoviesDao
import com.jorgetargz.tmdbcompose.data.models.entitys.toDataEntity
import com.jorgetargz.tmdbcompose.data.models.entitys.toDomain
import com.jorgetargz.tmdbcompose.data.remote.MoviesRemoteDataSource
import com.jorgetargz.tmdbcompose.domain.models.Movie
import com.jorgetargz.tmdbcompose.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repository which fetches Movies from Remote or Local data sources
 */
class MoviesRepository(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesDao: MoviesDao
) {

    fun fetchTrendingMovies(): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(trendingMoviesCached())
            emit(NetworkResult.Loading())
            val result = moviesRemoteDataSource.fetchTrendingMovies()
                .map { response -> response?.results
                    ?.sortedWith(compareByDescending { it.popularity })
                    ?.map { it.toDomain() }
                    ?: emptyList()
                }

            //Cache to database if response is successful
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    moviesDao.deleteAll()
                    moviesDao.insertAll(it.map { it.toDataEntity() })
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchTrendingMoviesCached(): Flow<NetworkResult<List<Movie>>> =
        flow {
            emit(trendingMoviesCached())
        }.flowOn(Dispatchers.IO)

    private fun trendingMoviesCached(): NetworkResult<List<Movie>> =
        moviesDao.getAll().let { list ->
            NetworkResult.Success(list.map { it.toDomain() })
        }

    fun fetchMovie(id: Int): Flow<NetworkResult<Movie>> {
        return flow {
            emit(movieCachedById(id))
            emit(NetworkResult.Loading())

            val result = moviesRemoteDataSource.fetchMovie(id)
                .map { response -> response?.toDomain() ?: Movie() }

            //Cache to database if response is successful
            if (result is NetworkResult.Success) {
                result.data?.let { it ->
                    moviesDao.update(it.toDataEntity())
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchMovieCached(id: Int): Flow<NetworkResult<Movie>> =
        flow {
            emit(movieCachedById(id))
        }.flowOn(Dispatchers.IO)

    private fun movieCachedById(id: Int): NetworkResult<Movie> =
        moviesDao.getById(id)?.let { movie ->
            NetworkResult.Success(movie.toDomain())
        } ?: NetworkResult.Success(Movie())
}