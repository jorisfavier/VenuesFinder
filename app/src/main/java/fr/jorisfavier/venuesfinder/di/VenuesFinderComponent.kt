package fr.jorisfavier.venuesfinder.di

import dagger.Component
import fr.jorisfavier.venuesfinder.ui.venuedetail.VenueDetailActivity
import fr.jorisfavier.venuesfinder.ui.venueslist.VenuesListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [VenuesFinderModule::class])
interface VenuesFinderComponent {
    fun inject(venuesListFragment: VenuesListFragment)
    fun inject(venueDetailActivity: VenueDetailActivity)
}