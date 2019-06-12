package fr.jorisfavier.venuesfinder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fr.jorisfavier.venuesfinder.ui.venueslist.VenuesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VenuesListFragment.newInstance())
                .commitNow()
        }
    }

}
