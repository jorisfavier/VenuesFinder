package fr.jorisfavier.venuesfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
