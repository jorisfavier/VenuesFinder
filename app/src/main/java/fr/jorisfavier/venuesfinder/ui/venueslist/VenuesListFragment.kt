package fr.jorisfavier.venuesfinder.ui.venueslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.VenuesFinderApp
import fr.jorisfavier.venuesfinder.databinding.VenuesListFragmentBinding
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
    private lateinit var binding: VenuesListFragmentBinding
    private var venueAdapter = VenuesListAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.venues_list_fragment,
            container,
            false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VenuesFinderApp.currentInstance?.appModule?.inject(this)
        viewModel = ViewModelProviders.of(this).get(VenuesListViewModel::class.java)
        viewModel.venuesManager = venuesManager
        initRecyclerView()
        initObserver()
        //We init the view with a blank search
        viewModel.searchVenues("")
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
    }

    private fun initObserver() {
        //We observe the search value to search for venues every times the user
        // enter a letter
        viewModel.search.observe(this, Observer { query ->
            viewModel.searchVenues(query)
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        venues_list.layoutManager = layoutManager
        venues_list.addItemDecoration(DividerItemDecoration(context,layoutManager.orientation))
        venues_list.adapter = venueAdapter

        viewModel.getVenues().observe(this, Observer { venues ->
            venueAdapter.updateVenueList(venues)
        })
    }
}
