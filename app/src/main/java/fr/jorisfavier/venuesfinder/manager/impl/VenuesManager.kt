package fr.jorisfavier.venuesfinder.manager.impl

import fr.jorisfavier.venuesfinder.api.FoursquareService
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO
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

}