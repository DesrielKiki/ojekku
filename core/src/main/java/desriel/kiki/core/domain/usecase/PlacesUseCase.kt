package desriel.kiki.core.domain.usecase

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import desriel.kiki.core.data.source.remote.dto.response.PlacesResponse
import kotlinx.coroutines.flow.Flow


interface PlacesUseCase {
  suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse?>>
  suspend fun getPlaceRoutes(origin: String, destination: String): Flow<Resource<GetPlacesRoutesResponse>>
}