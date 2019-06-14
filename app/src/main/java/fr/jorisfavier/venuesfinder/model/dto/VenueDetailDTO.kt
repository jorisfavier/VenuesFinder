package fr.jorisfavier.venuesfinder.model.dto

data class VenueDetailDTO(
    val id : String,
    val name : String,
    val contact : ContactDTO?,
    val location : LocationDTO,
    val rating : Double?,
    val ratingColor : String?,
    val description : String?,
    val bestPhoto : BestPhotoDTO?)