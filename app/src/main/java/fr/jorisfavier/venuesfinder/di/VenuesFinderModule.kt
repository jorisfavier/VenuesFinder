package fr.jorisfavier.venuesfinder.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fr.jorisfavier.venuesfinder.VenuesFinderDb
import fr.jorisfavier.venuesfinder.api.FoursquareService
import fr.jorisfavier.venuesfinder.dao.VenueDao
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

    @Singleton
    @Provides
    fun provideDb(app: Application): VenuesFinderDb {
        return Room.databaseBuilder(app, VenuesFinderDb::class.java, "venueFinder.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideVenueDao(db: VenuesFinderDb): VenueDao {
        return db.venueDao()
    }
}