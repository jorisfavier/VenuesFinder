package fr.jorisfavier.venuesfinder.ui.venueslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.jorisfavier.venuesfinder.R
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.utils.inflate

class VenuesListAdapter(private var venuesList: List<Venue>) : RecyclerView.Adapter<VenueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val view = parent.inflate(R.layout.venue_viewholder, false)
        return VenueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return venuesList.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(venuesList[position])
    }

    fun updateVenueList(venues: List<Venue>){
        venuesList = venues
        notifyDataSetChanged()
    }

}