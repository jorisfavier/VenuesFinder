package fr.jorisfavier.venuesfinder.model

import fr.jorisfavier.venuesfinder.model.dto.VenueDTO

class Venue(
    val id: String,
    val name: String,
    val address: String
) {
    companion object {
        fun fromVenueDTO(venueDTO: VenueDTO): Venue {
            return Venue(venueDTO.id,
                venueDTO.name,
                venueDTO.location.formattedAddress.joinToString(separator = "\n"))
        }
    }
}