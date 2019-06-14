package fr.jorisfavier.venuesfinder.model

import fr.jorisfavier.venuesfinder.model.dto.VenueDTO
import fr.jorisfavier.venuesfinder.model.dto.VenueDetailDTO

class Venue(
    val id: String,
    val name: String,
    val address: String,
    val phone: String?,
    val rating: Double?,
    val ratingColor: String?,
    val description: String?,
    val photoUrl: String?
) {
    companion object {
        fun fromVenueDTO(venueDTO: VenueDTO): Venue {
            return Venue(venueDTO.id,
                venueDTO.name,
                venueDTO.location.formattedAddress.joinToString(separator = "\n"),
                null,
                null,
                null,
                null,
                null)
        }

        fun fromVenueDetailDTO(venueDetailDTO: VenueDetailDTO): Venue {
            return Venue(venueDetailDTO.id,
                venueDetailDTO.name,
                venueDetailDTO.location.formattedAddress.joinToString(separator = "\n"),
                venueDetailDTO.contact?.formattedPhone,
                venueDetailDTO.rating,
                venueDetailDTO.ratingColor,
                venueDetailDTO.description,
                venueDetailDTO.bestPhoto?.prefix+"original"+venueDetailDTO.bestPhoto?.suffix)
        }
    }
}