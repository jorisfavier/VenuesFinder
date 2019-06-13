package fr.jorisfavier.venuesfinder

import android.app.Application
import fr.jorisfavier.venuesfinder.di.DaggerVenuesFinderComponent
import fr.jorisfavier.venuesfinder.di.VenuesFinderComponent
import fr.jorisfavier.venuesfinder.di.VenuesFinderModule

class VenuesFinderApp : Application() {
    companion object {
        var currentInstance: VenuesFinderApp? = null
    }

    var appModule: VenuesFinderComponent? = null
    private set

    override fun onCreate() {
        super.onCreate()
        currentInstance = this
        appModule = DaggerVenuesFinderComponent
            .builder()
            .venuesFinderModule(VenuesFinderModule(this))
            .build()

    }
}