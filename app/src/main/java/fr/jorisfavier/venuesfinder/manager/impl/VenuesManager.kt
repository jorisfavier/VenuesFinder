package fr.jorisfavier.venuesfinder.manager.impl

import fr.jorisfavier.venuesfinder.api.FoursquareService
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.dto.*
import retrofit2.Callback
import javax.inject.Inject

class VenuesManager @Inject constructor(private val fsqrService: FoursquareService): IVenuesManager {

    override fun searchVenues(search: String, callback: Callback<FsqrResponseDTO<VenuesSearchResultDTO>>) {
        fsqrService.searchVenues(FoursquareService.rotterdamLL,
            "checkin",
            FoursquareService.radius,
            search,
            FoursquareService.limit).enqueue(callback)
    }

    override fun getVenueDetail(id: String, callback: Callback<FsqrResponseDTO<VenueDetailResultDTO>>) {
        fsqrService.getMovieDetail(id).enqueue(callback)
    }
}