package fr.jorisfavier.venuesfinder.api

import fr.jorisfavier.venuesfinder.BuildConfig
import fr.jorisfavier.venuesfinder.model.dto.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareService {
    @GET("venues/search")
    fun searchVenues(@Query("ll") ll: String,
                     @Query("intent") intent: String,
                     @Query("radius") radius: Int,
                     @Query("query") search: String,
                     @Query("limit") limit: Int) : Call<FsqrResponseDTO<VenuesSearchResultDTO>>

    @GET("venues/{id}")
    fun getMovieDetail(@Path("id") id: String): Call<FsqrResponseDTO<VenueDetailResultDTO>>

    companion object {
        val apiSecret = BuildConfig.FoursquareSecret
        val apiClientId = BuildConfig.FoursquareClientId
        val apiVersion = "20190425"
        val rotterdamLL = "51.9225, 4.47917"
        val radius = 1000
        val limit = 10
        val baseUrl = "https://api.foursquare.com/v2/"

        fun create(): FoursquareService {
            //We create an interceptor to add the clien_id, client_secret et version to each
            //call for this API
            val interceptor = Interceptor { chain ->
                val newUrl = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("client_id", apiClientId)
                    .addQueryParameter("client_secret", apiSecret)
                    .addQueryParameter("v", apiVersion).build()
                val requestBuilder = chain.request().newBuilder()
                    .url(newUrl)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build()

            return retrofit.create(FoursquareService::class.java)
        }
    }
}