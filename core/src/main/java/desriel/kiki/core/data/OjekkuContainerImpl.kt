package desriel.kiki.core.data

import android.content.Context
import desriel.kiki.core.data.interactors.AuthInteractor
import desriel.kiki.core.data.interactors.PlacesInteractor
import desriel.kiki.core.data.interactors.UserInteractor
import desriel.kiki.core.data.repository.AuthRepositoryImpl
import desriel.kiki.core.data.repository.PlacesRepositoryImpl
import desriel.kiki.core.data.repository.UserRepositoryImpl
import desriel.kiki.core.data.source.local.datastore.OjekkuDataStore
import desriel.kiki.core.data.source.local.room.OjekkuDatabase
import desriel.kiki.core.data.source.remote.network.OjekkuService
import desriel.kiki.core.domain.OjekkuContainer
import desriel.kiki.core.domain.repository.AuthRepository
import desriel.kiki.core.domain.repository.PlacesRepository
import desriel.kiki.core.domain.repository.UserRepository
import desriel.kiki.core.domain.usecase.AuthUseCase
import desriel.kiki.core.domain.usecase.PlacesUseCase
import desriel.kiki.core.domain.usecase.UserUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OjekkuContainerImpl constructor(
  private val context: Context
) : OjekkuContainer {

  private val ojekkuDatabase: OjekkuDatabase by lazy {
    OjekkuDatabase.getInstance(context)
  }

  private val ojekkuDataStore: OjekkuDataStore by lazy {
    OjekkuDataStore(context)
  }

  private fun retrofitClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(logging).build()
  }

  private val retrofitPlaces: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://places.googleapis.com")
    .client(retrofitClient())
    .build()

  private val jekyApiService: OjekkuService by lazy {
    retrofitPlaces.create(OjekkuService::class.java)
  }

  override val authRepository: AuthRepository
    get() = AuthRepositoryImpl(ojekkuDatabase.userDao())

  override val userRepository: UserRepository
    get() = UserRepositoryImpl(ojekkuDataStore)

  override val placesRepository: PlacesRepository
    get() = PlacesRepositoryImpl(jekyApiService)

  override val authUseCase: AuthUseCase
    get() = AuthInteractor(authRepository)

  override val userUseCase: UserUseCase
    get() = UserInteractor(userRepository)

  override val placesUseCase: PlacesUseCase
    get() = PlacesInteractor(placesRepository)
}