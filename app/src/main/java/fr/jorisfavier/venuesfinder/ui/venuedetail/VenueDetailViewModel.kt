package fr.jorisfavier.venuesfinder.ui.venuedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenueDetailResultDTO
import retrofit2.Call
import retrofit2.Response

class VenueDetailViewModel: ViewModel(){
    lateinit var venueManager: IVenuesManager

    private var _venue = MutableLiveData<Venue>()
    private val _error: MutableLiveData<String?> = MutableLiveData()

    fun getVenue(): LiveData<Venue> = _venue
    fun getError(): LiveData<String?> = _error

    fun loadDetail(venueId: String){
        venueManager.getVenueDetail(venueId, object : retrofit2.Callback<FsqrResponseDTO<VenueDetailResultDTO>>{
            override fun onFailure(call: Call<FsqrResponseDTO<VenueDetailResultDTO>>, t: Throwable) {
                _error.value = "Unable to get venue detail"
            }

            override fun onResponse(
                call: Call<FsqrResponseDTO<VenueDetailResultDTO>>,
                response: Response<FsqrResponseDTO<VenueDetailResultDTO>>
            ) {
                if(response.body() == null){
                    _error.value = "Unable to get venue detail"
                }
                _venue.value = Venue.fromVenueDetailDTO(response.body()!!.response.venue)
            }
        })
    }
}