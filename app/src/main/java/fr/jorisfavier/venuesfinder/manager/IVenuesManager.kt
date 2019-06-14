package fr.jorisfavier.venuesfinder.manager

import fr.jorisfavier.venuesfinder.model.dto.*
import retrofit2.Callback

interface IVenuesManager {
    /**
     * Retrieve the venues based on the search keywords
     * @param search : A search term to be applied against venue names.
     * @param callback : callback when the venues are retrieved
     */
    fun searchVenues(search: String,
                     callback: retrofit2.Callback<FsqrResponseDTO<VenuesSearchResultDTO>>)

    /***
     * Gives the full details about a venue including location, tips, and categories
     * @param id : id of the venue to retrieve
     * @param callback : callback when the venue detail is retrieved
     */
    fun getVenueDetail(id: String, callback: Callback<FsqrResponseDTO<VenueDetailResultDTO>>)
}