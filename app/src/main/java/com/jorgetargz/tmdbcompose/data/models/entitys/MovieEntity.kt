package com.jorgetargz.tmdbcompose.data.models.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgetargz.tmdbcompose.domain.models.Movie
import java.time.LocalDate

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String? = null,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0,
)

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    releaseDate = LocalDate.parse(release_date),
    voteAverage = vote_average,
    voteCount = vote_count,
)

fun Movie.toDataEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    popularity = popularity,
    poster_path = posterPath,
    release_date = releaseDate.toString(),
    vote_average = voteAverage,
    vote_count = voteCount,
)
