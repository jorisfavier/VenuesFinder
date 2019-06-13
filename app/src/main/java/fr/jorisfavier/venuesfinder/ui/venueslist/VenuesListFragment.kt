package fr.jorisfavier.venuesfinder.ui.venueslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.VenuesFinderApp
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import javax.inject.Inject

class VenuesListFragment : Fragment() {

    companion object {
        fun newInstance() = VenuesListFragment()
    }

    @Inject
    lateinit var venuesManager: IVenuesManager

    private lateinit var viewModel: VenuesListViewModel

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
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadData()
    }

}
