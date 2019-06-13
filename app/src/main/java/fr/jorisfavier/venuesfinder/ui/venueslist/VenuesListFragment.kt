package fr.jorisfavier.venuesfinder.ui.venueslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.VenuesFinderApp
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import kotlinx.android.synthetic.main.venues_list_fragment.*
import javax.inject.Inject

class VenuesListFragment : Fragment() {

    companion object {
        fun newInstance() = VenuesListFragment()
    }

    @Inject
    lateinit var venuesManager: IVenuesManager

    private lateinit var viewModel: VenuesListViewModel
    private var venueAdapter = VenuesListAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.venues_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VenuesFinderApp.currentInstance?.appModule?.inject(this)
        viewModel = ViewModelProviders.of(this).get(VenuesListViewModel::class.java)
        viewModel.venuesManager = venuesManager
        initErrorHandler()
        initRecyclerView()
        viewModel.loadData()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        venues_list.layoutManager = layoutManager
        venues_list.addItemDecoration(DividerItemDecoration(context,layoutManager.orientation))
        venues_list.adapter = venueAdapter

        viewModel.getVenues().observe(this, Observer { venues ->
            if (venues.size > 0) {
                list_loading.visibility = View.GONE
                venueAdapter.updateVenueList(venues)
            }
        })
    }

    private fun initErrorHandler(){
        viewModel.getError().observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                list_error.text = errorMessage
                list_error.visibility = View.VISIBLE
            }
        })
    }

}
