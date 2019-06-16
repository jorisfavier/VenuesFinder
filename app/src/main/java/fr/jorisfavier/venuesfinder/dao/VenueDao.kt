package fr.jorisfavier.venuesfinder.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.jorisfavier.venuesfinder.model.Venue

@Dao
interface VenueDao {
    /***
     *  Search for venues in the database based on his name
     *  @param query the venue's name to search for
     *  @return a list of venue
     */
    @Query("SELECT * from Venue where name like :query")
    fun findVenuesByName(query: String): LiveData<List<Venue>>

    /**
     * Search for a venue based on his id
     * @param id : the venue identifier
     * @return the founded venue or null
     */
    @Query("SELECT * from Venue where id = :id")
    fun findVenueById(id: String): LiveData<Venue?>

    /**
     * Insert the given venues in the database
     * but if one already exists it will be not updated
     * @param venues: the venues to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg venues: Venue)

    /**
     * Update a venue in the database
     * @param venue : the venue to update
     */
    @Update
    fun udpateVenue(venue: Venue)
}