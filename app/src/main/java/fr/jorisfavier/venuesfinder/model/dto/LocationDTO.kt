package fr.jorisfavier.venuesfinder.model.dto

data class LocationDTO (
    val address : String,
    val lat : Double,
    val lng : Double,
    val distance : Int,
    val postalCode : String,
    val cc : String,
    val city : String,
    val state : String,
    val country : String,
    val formattedAddress : List<String>
)