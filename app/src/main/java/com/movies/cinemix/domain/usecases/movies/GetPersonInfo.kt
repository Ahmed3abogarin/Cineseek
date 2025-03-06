package com.movies.cinemix.domain.usecases.movies

import com.movies.cinemix.domain.model.PersonResponse
import com.movies.cinemix.domain.repository.MoviesRepository

class GetPersonInfo (
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(personId: Int): PersonResponse{
        return moviesRepository.getPersonInfo(personId)
    }
}