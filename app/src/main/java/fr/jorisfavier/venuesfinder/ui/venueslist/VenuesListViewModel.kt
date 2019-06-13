package fr.jorisfavier.venuesfinder.ui.venueslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO
import retrofit2.Call
import retrofit2.Response

class VenuesListViewModel : ViewModel() {
    lateinit var venuesManager: IVenuesManager

    private val _venues: MutableLiveData<ArrayList<Venue>> = MutableLiveData()
    private val _error: MutableLiveData<String?> = MutableLiveData()

    fun loadData(){
        venuesManager.searchVenues("coffee", object: retrofit2.Callback<FsqrResponseDTO<VenuesSearchResultDTO>>{
            override fun onFailure(call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>, t: Throwable) {
                _error.postValue("An error occured")
            }

            override fun onResponse(
                call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>,
                response: Response<FsqrResponseDTO<VenuesSearchResultDTO>>
            ) {
                var test = response.body()?.response?.venues
            }
        })
    }
}
