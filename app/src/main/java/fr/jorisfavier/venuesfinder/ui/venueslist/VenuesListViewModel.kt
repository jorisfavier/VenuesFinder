package fr.jorisfavier.venuesfinder.ui.venueslist

import androidx.lifecycle.*
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO
import retrofit2.Call
import retrofit2.Response

class VenuesListViewModel : ViewModel() {
    lateinit var venuesManager: IVenuesManager

    private val _venues: MutableLiveData<List<Venue>> = MutableLiveData()
    private val _error: MutableLiveData<String?> = MutableLiveData()
    private val _showError: MutableLiveData<Boolean> = MutableLiveData()
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val search =  MutableLiveData<String>()

    fun getVenues(): LiveData<List<Venue>> = _venues
    fun getError(): LiveData<String?> = _error
    fun getLoading(): LiveData<Boolean> = _loading
    fun showError(): LiveData<Boolean> = _showError

    /***
     * Search for the venues corresponding to the given query
     * @param query : A search term to be applied against venue names.
     */
    fun searchVenues(query: String){
        _loading.value = true
        _showError.value = false
        venuesManager.searchVenues(query, object: retrofit2.Callback<FsqrResponseDTO<VenuesSearchResultDTO>>{
            override fun onFailure(call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>, t: Throwable) {
                _error.value = "An error occured"
                _loading.value = false
                _showError.value = true
                _venues.value = ArrayList()
            }

            override fun onResponse(
                call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>,
                response: Response<FsqrResponseDTO<VenuesSearchResultDTO>>
            ) {
                _loading.value = false
                _showError.value = false
                _venues.value = response.body()?.response?.venues?.map { Venue.fromVenueDTO(it) }
            }
        })
    }



}
