package fr.jorisfavier.venuesfinder.ui.venuedetail

import androidx.lifecycle.*
import fr.jorisfavier.venuesfinder.dao.VenueDao
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenueDetailResultDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class VenueDetailViewModel: ViewModel(){
    lateinit var venueManager: IVenuesManager
    lateinit var venueDao: VenueDao
    lateinit var venueId: String

    private var _venue = MutableLiveData<Venue>()
    private val _error: MutableLiveData<String?> = MutableLiveData()

    val venue: LiveData<Venue?> = Transformations.switchMap(_venue) { venue ->
        //if venue is null it means that an error occured while retrieving the venue's data
        //so we look for the data in the db
        if(venue == null){
            venueDao.findVenueById(venueId)
        }
        else {
            MutableLiveData(venue)
        }
    }
    fun getError(): LiveData<String?> = _error

    fun loadDetail(venueId: String){
        this.venueId = venueId
        venueManager.getVenueDetail(venueId, object : retrofit2.Callback<FsqrResponseDTO<VenueDetailResultDTO>>{
            override fun onFailure(call: Call<FsqrResponseDTO<VenueDetailResultDTO>>, t: Throwable) {
                _error.value = "Unable to get venue detail"
                _venue.value = null
            }

            override fun onResponse(
                call: Call<FsqrResponseDTO<VenueDetailResultDTO>>,
                response: Response<FsqrResponseDTO<VenueDetailResultDTO>>
            ) {
                if(response.body() == null){
                    _error.value = "There is no detail for this venue"
                    _venue.value = null
                }
                else {
                    //if we get the venue detail we insert it in the database to get it offline
                    _venue.value = Venue.fromVenueDetailDTO(response.body()!!.response.venue)
                    update(_venue.value!!)
                }
            }
        })
    }

    private fun update(venue: Venue) = viewModelScope.launch(Dispatchers.IO) {
        venueDao.udpateVenue(venue)
    }
}