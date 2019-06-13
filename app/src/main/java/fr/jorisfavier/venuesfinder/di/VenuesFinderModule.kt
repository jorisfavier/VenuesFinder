package fr.jorisfavier.venuesfinder.di

import android.app.Application
import dagger.Module
import dagger.Provides
import fr.jorisfavier.venuesfinder.api.FoursquareService
import fr.jorisfavier.venuesfinder.manager.IVenuesManager
import fr.jorisfavier.venuesfinder.manager.impl.VenuesManager
import javax.inject.Singleton

@Module
class VenuesFinderModule(val application: Application) {

    @Singleton
    @Provides
    fun applicationProvider(): Application {
        return application
    }

    @Singleton
    @Provides
    fun foursquareServiceProvider(): FoursquareService {
        return FoursquareService.create()
    }

    @Singleton
    @Provides
    fun venueManagerProvider(fsqrService: FoursquareService): IVenuesManager {
        return VenuesManager(fsqrService)
    }
}