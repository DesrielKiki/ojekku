package desriel.kiki.core.data.interactors

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import desriel.kiki.core.data.source.remote.dto.response.PlacesResponse
import desriel.kiki.core.domain.repository.PlacesRepository
import desriel.kiki.core.domain.usecase.PlacesUseCase
import kotlinx.coroutines.flow.Flow


class PlacesInteractor constructor(
  private val repository: PlacesRepository
): PlacesUseCase {
  override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
    return repository.getPlaces(keyword)
  }

  override suspend fun getPlaceRoutes(
    origin: String, destination: String
  ): Flow<Resource<GetPlacesRoutesResponse>> {
    return repository.getPlacesRoute(origin, destination)
  }
}