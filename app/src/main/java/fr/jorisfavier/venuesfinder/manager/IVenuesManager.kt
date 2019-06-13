package fr.jorisfavier.venuesfinder.manager

import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO

interface IVenuesManager {
    /**
     * Retrieve the venues based on the search keywords
     * @param search : A search term to be applied against venue names.
     * @param callback : callback when the venues are retrieved
     */
    fun searchVenues(search: String,
                     callback: retrofit2.Callback<FsqrResponseDTO<VenuesSearchResultDTO>>)
}