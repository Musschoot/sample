package training.squads.fitnessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import training.squads.fitnessapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        // Disable fragment labels for toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)

        val navController = this.findNavController(R.id.myNavHostFragment)

        // Display navigation drawer
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

               // prevent nav gesture if not on start destination
              navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
                   if (nd.id == nc.graph.startDestination) {
                       drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                   } else {
                       drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                   }

                  Log.i("MAIN NAV","Test main nav 1")
               }
        NavigationUI.setupWithNavController(binding.drawerView, navController)

        // Display bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)


        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.sessions, R.id.reservations, R.id.evolution), drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}