package fr.jorisfavier.venuesfinder.model.dto

data class BestPhotoDTO (

    val id : String,
    val createdAt : Int,
    val prefix : String,
    val suffix : String,
    val width : Int,
    val height : Int,
    val visibility : String
)