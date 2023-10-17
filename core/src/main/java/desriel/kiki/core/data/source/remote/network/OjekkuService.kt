package desriel.kiki.core.data.source.remote.network

import desriel.kiki.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import desriel.kiki.core.data.source.remote.dto.response.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url


interface OjekkuService {

  companion object {
    const val KEY : String = "AIzaSyBu6mS62zBS9zSu4NFxKWcJZKqzgfh-8d0"
  }

  @Headers(
    "X-Goog-Api-Key: $KEY",
    "X-Goog-FieldMask: places.displayName,places.formattedAddress,places.priceLevel,places.location"
  )
  @POST("v1/places:searchText")
  suspend fun getPlaces(
    @Query("textQuery") textQuery: String
  ): PlacesResponse

  @GET
  suspend fun getPlaceRoutes(
    @Url url: String,
    @Query("origin") origin: String,
    @Query("destination") destination: String,
    @Query("sensor") sensor: Boolean = false,
    @Query("mode") mode: String = "driving",
    @Query("key") key: String = KEY,
  ): GetPlacesRoutesResponse

}