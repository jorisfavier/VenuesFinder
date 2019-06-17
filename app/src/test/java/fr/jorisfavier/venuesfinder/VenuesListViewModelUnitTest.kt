package fr.jorisfavier.venuesfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.junit.Rule
import com.nhaarman.mockitokotlin2.*
import fr.jorisfavier.venuesfinder.dao.VenueDao
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.model.dto.FsqrResponseDTO
import fr.jorisfavier.venuesfinder.model.dto.LocationDTO
import fr.jorisfavier.venuesfinder.model.dto.VenueDTO
import fr.jorisfavier.venuesfinder.model.dto.VenuesSearchResultDTO
import fr.jorisfavier.venuesfinder.ui.venueslist.VenuesListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Callback
import retrofit2.Response


class VenuesListViewModelUnitTest {
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private var venuesManager: IVenuesManager = mock()
    private var venueDao: VenueDao = mock()
    private var locationDTO = LocationDTO("test",
        0.0,
        0.0,
        0,
        "54250",
        "cc",
        "Paris",
        "France",
        "France",
        ArrayList())

    private val validVenueDTOlist = listOf(VenueDTO("123456","test", locationDTO))
    private val viewmodel = VenuesListViewModel()
    lateinit var searchVenuesResponse: FsqrResponseDTO<VenuesSearchResultDTO>

    init {

        viewmodel.venueDao = venueDao
        viewmodel.venuesManager = venuesManager
        doAnswer { invocationOnMock ->
            val callback = invocationOnMock.getArgument<Callback<FsqrResponseDTO<VenuesSearchResultDTO>>>(1)
            callback.onResponse(mock(), Response.success(searchVenuesResponse))
            null
        }.`when`(venuesManager).searchVenues(any(), any())
    }


    @Test
    fun `search with internet connection should emit venues`() {
        //given
        searchVenuesResponse = FsqrResponseDTO(VenuesSearchResultDTO(validVenueDTOlist))
        val mockVenuesObserver : Observer<List<Venue>> = mock()
        viewmodel.venues.observeForever(mockVenuesObserver)
        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        runBlocking {
            viewmodel.searchVenues("test")
        }

        //then
        verify(venuesManager).searchVenues(any(), any())
        verify(mockVenuesObserver).onChanged(check {
            assert(it.count() > 0)
        })
        verify(mockErrorObserver, never()).onChanged(any())
        verify(venueDao).insertAll(any())
    }

    @Test
    fun `search with internet connection with empty venues should emit error`() {
        //given
        searchVenuesResponse = FsqrResponseDTO(VenuesSearchResultDTO(ArrayList()))
        val mockVenuesObserver : Observer<List<Venue>> = mock()
        viewmodel.venues.observeForever(mockVenuesObserver)
        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        runBlocking {
            viewmodel.searchVenues("test")
        }

        //then
        verify(venuesManager).searchVenues(any(), any())
        verify(venueDao, never()).insertAll(any())
        verify(mockVenuesObserver).onChanged(check {
            assert(it.count() == 0)
        })
        verify(mockErrorObserver).onChanged(any())
    }

    @Test
    fun `search without internet connection should emit venues from database`() {
        //given
        doAnswer { invocationOnMock ->
            val callback = invocationOnMock.getArgument<Callback<FsqrResponseDTO<VenuesSearchResultDTO>>>(1)
            callback.onFailure(mock(),mock())
            null
        }.`when`(venuesManager).searchVenues(any(), any())
        whenever(venueDao.findVenuesByName(any())) doReturn
                MutableLiveData(validVenueDTOlist.map { Venue.fromVenueDTO(it) })
        val mockVenuesObserver : Observer<List<Venue>> = mock()
        viewmodel.venues.observeForever(mockVenuesObserver)
        val mockErrorObserver : Observer<String?> = mock()
        viewmodel.getError().observeForever(mockErrorObserver)

        //when
        runBlocking {
            viewmodel.searchVenues("test")
        }

        //then
        verify(venuesManager).searchVenues(any(), any())
        verify(venueDao, never()).insertAll(any())
        verify(venueDao).findVenuesByName(any())
        verify(mockVenuesObserver).onChanged(check {
            assert(it.count() > 0)
        })
        verify(mockErrorObserver).onChanged(any())
    }
}
