package fr.jorisfavier.venuesfinder

import android.app.Application

class VenuesFinderApp : Application() {
    companion object {
        var currentInstance: VenuesFinderApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        currentInstance = this
    }
}