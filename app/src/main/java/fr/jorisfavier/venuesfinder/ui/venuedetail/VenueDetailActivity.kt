package fr.jorisfavier.venuesfinder.ui.venuedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.VenuesFinderApp
import fr.jorisfavier.venuesfinder.databinding.VenueDetailActivityBinding
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import kotlinx.android.synthetic.main.venue_detail_activity.*
import javax.inject.Inject

class VenueDetailActivity: AppCompatActivity() {

    companion object {
        val venueIdExtra = "venueId"
    }

    private lateinit var viewModel: VenueDetailViewModel
    @Inject
    lateinit var venueManager: IVenuesManager
    private var venueId: String? = null



    private fun initObserver(){
        viewModel.getVenue().observe(this, Observer { venue ->
            Glide.with(this)
                .load(venue.photoUrl)
                .placeholder(ColorDrawable(Color.GRAY))
                .centerCrop()
                .into(detail_image)
        })
    }

    private fun displayError(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        venueId = intent.getStringExtra(venueIdExtra)

        val binding = DataBindingUtil.setContentView<VenueDetailActivityBinding>(this,
            R.layout.venue_detail_activity)
        VenuesFinderApp.currentInstance?.appModule?.inject(this)
        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)
        viewModel.venueManager = venueManager
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        initObserver()
        if(venueId != null && !venueId!!.isEmpty()) {
            viewModel.loadDetail(venueId!!)
        }
        else {
            displayError()
        }
    }
}