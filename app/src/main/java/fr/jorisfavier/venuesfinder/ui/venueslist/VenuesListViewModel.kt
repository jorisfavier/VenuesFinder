package fr.jorisfavier.venuesfinder.ui.venueslist

import androidx.lifecycle.*
import fr.jorisfavier.venuesfinder.dao.VenueDao
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class VenuesListViewModel : ViewModel() {
    lateinit var venuesManager: IVenuesManager
    lateinit var venueRepo: VenueDao

    private val _venues: MutableLiveData<List<Venue>> = MutableLiveData()
    private val _error: MutableLiveData<String?> = MutableLiveData()

    val venues: LiveData<List<Venue>> = Transformations.switchMap(_venues) { venues ->
        //if venues is null then it means that we were unable to retrieve it from internet
        //so we look for venues within the database
        if(venues == null){
             venueRepo.findVenuesByName((search.value?: "")+"%")
        }
        else {
             MutableLiveData(venues)
        }
    }
    val search =  MutableLiveData<String>()
    fun getError(): LiveData<String?> = _error

    /***
     * Search for the venues corresponding to the given query
     * @param query : A search term to be applied against venue names.
     */
    fun searchVenues(query: String){
        venuesManager.searchVenues(query, object: retrofit2.Callback<FsqrResponseDTO<VenuesSearchResultDTO>>{
            override fun onFailure(call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>, t: Throwable) {
                _error.value = "Sorry we are unable to retrieve the venues from the internet."
                _venues.value = null
            }

            override fun onResponse(
                call: Call<FsqrResponseDTO<VenuesSearchResultDTO>>,
                response: Response<FsqrResponseDTO<VenuesSearchResultDTO>>
            ) {
                val result = response.body()?.response?.venues?.map { Venue.fromVenueDTO(it) }
                _venues.value = result
                if(result != null){
                    insert(*result.toTypedArray())
                    if(result.count() == 0){
                        _error.value = "Sorry! No venues found matching your query"
                    }
                }
            }
        })
    }

    /**
     * Insert
     */
    private fun insert(vararg venues: Venue) = viewModelScope.launch(Dispatchers.IO) {
        venueRepo.insertAll(*venues)
    }
}
