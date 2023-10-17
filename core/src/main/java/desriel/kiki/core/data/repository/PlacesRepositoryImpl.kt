package desriel.kiki.core.data.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.remote.SafeApiCall
import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import desriel.kiki.core.data.source.remote.dto.response.PlacesResponse
import desriel.kiki.core.data.source.remote.network.OjekkuService
import desriel.kiki.core.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class PlacesRepositoryImpl constructor(
  private val apiService: OjekkuService
): PlacesRepository, SafeApiCall {

  override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
    return flow {
      emit( safeApiCall { apiService.getPlaces(keyword) } )
    }
  }

  override suspend fun getPlacesRoute(
    origin: String, destination: String
  ): Flow<Resource<GetPlacesRoutesResponse>> {
    return flow {
      emit( safeApiCall { apiService.getPlaceRoutes("https://maps.googleapis.com/maps/api/directions/json", origin, destination) } )
    }
  }

}