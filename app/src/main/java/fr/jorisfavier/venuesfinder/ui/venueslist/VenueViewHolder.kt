package fr.jorisfavier.venuesfinder.ui.venueslist

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import fr.jorisfavier.venuesfinder.model.Venue
import fr.jorisfavier.venuesfinder.ui.venuedetail.VenueDetailActivity
import kotlinx.android.synthetic.main.venue_viewholder.view.*

class VenueViewHolder(venueView: View): RecyclerView.ViewHolder(venueView) {

    private var view = itemView

    fun bind(venue: Venue) {
        view.venue_name.text = venue.name
        view.venue_address.text = venue.address
        view.setOnClickListener {
            val intent = Intent(view.context,VenueDetailActivity::class.java).apply {
                putExtra(VenueDetailActivity.venueIdExtra,venue.id)
            }
            view.context.startActivity(intent)
        }
    }
}