package fr.jorisfavier.venuesfinder

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.jorisfavier.venuesfinder.dao.VenueDao
import fr.jorisfavier.venuesfinder.model.Venue

@Database(entities = arrayOf(Venue::class), version = 1)
abstract class VenuesFinderDb : RoomDatabase() {
    abstract fun venueDao(): VenueDao
}