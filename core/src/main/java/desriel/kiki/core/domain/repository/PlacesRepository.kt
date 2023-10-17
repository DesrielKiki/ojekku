package desriel.kiki.core.domain.repository

import desriel.kiki.core.data.source.Resource
import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import desriel.kiki.core.data.source.remote.dto.response.PlacesResponse
import kotlinx.coroutines.flow.Flow


interface PlacesRepository {
  suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>>
  suspend fun getPlacesRoute(origin: String, destination: String): Flow<Resource<GetPlacesRoutesResponse>>
}