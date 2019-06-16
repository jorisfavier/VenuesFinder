package fr.jorisfavier.venuesfinder.ui.venuedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.VenuesFinderApp
import fr.jorisfavier.venuesfinder.dao.VenueDao
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
    @Inject
    lateinit var venueDao: VenueDao
    private var venueId: String? = null



    private fun initObserver(){
        viewModel.venue.observe(this, Observer { venue ->
            if(venue != null){
                Glide.with(this)
                    .load(venue.photoUrl)
                    .placeholder(ColorDrawable(Color.GRAY))
                    .centerCrop()
                    .into(detail_image)
            }
            else if(viewModel.getError().value != null) {
                //if venue is null it means that we were unable to find the venue detail with the Api and with the db
                //so we display an error
                displayError(viewModel.getError().value!!)
            }
        })
    }

    /**
     * Show an error message using a toast and close the activity
     * @param errorMessage : the message to display in the toast
     */
    private fun displayError(errorMessage: String){
        Toast.makeText(baseContext,errorMessage, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        venueId = intent.getStringExtra(venueIdExtra)

        val binding = DataBindingUtil.setContentView<VenueDetailActivityBinding>(this,
            R.layout.venue_detail_activity)

        VenuesFinderApp.currentInstance?.appModule?.inject(this)

        viewModel = ViewModelProviders.of(this).get(VenueDetailViewModel::class.java)
        viewModel.venueManager = venueManager
        viewModel.venueDao = venueDao
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        initObserver()
        if(venueId != null && !venueId!!.isEmpty()) {
            viewModel.loadDetail(venueId!!)
        }
        else {
            displayError("An error occured")
        }
    }
}