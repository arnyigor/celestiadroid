package com.arny.celestiatools.presenter.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.arny.celestiatools.R
import com.arny.celestiatools.data.utils.replaceFragmentInActivity
import com.arny.celestiatools.presenter.asteroids.AsteroidsFragment
import com.arny.celestiatools.presenter.orbitcalc.OrbitCalcFragment
import com.arny.celestiatools.presenter.planets.PlanetsFragment
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem

class MainActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var drawer: Drawer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initDrawer(savedInstanceState)

        //		String nasaApiKey = ApiConstants.NASA_API_KEY;
        //		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa api_key = " + nasaApiKey);
        //		String hex = CryptoFiles.generateHexKey("com.arny.celestiatools");
        //		String encryped = CryptoFiles.encryptString(hex, nasaApiKey);
        //		Log.i(MainActivity.class.getSimpleName(), "onCreate: hex = " + hex);
        //		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa encrypted = " + encryped);
        //		Log.i(MainActivity.class.getSimpleName(), "onCreate: nasa decrypted = " + CryptoFiles.decryptString(hex,encryped));
    }

    private fun initDrawer(savedInstanceState: Bundle?) {
        drawer = DrawerBuilder()
                .withActivity(this)
                .withOnDrawerListener(object : Drawer.OnDrawerListener {
                    override fun onDrawerOpened(drawerView: View) {

                    }

                    override fun onDrawerClosed(drawerView: View) {

                    }

                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                    }
                })
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar!!)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        PrimaryDrawerItem()
                                .withIdentifier(MENU_ORBIT_CALC.toLong())
                                .withName(R.string.calc_orbit_fragment)
                                .withIcon(GoogleMaterial.Icon.gmd_3d_rotation),
                        PrimaryDrawerItem()
                                .withIdentifier(MENU_ASTEROIDS.toLong())
                                .withName(R.string.fragment_asteroids)
                                .withIcon(GoogleMaterial.Icon.gmd_flight_takeoff),
                        PrimaryDrawerItem()
                                .withIdentifier(MENU_PLANETS.toLong())
                                .withName(R.string.planets)
                                .withIcon(GoogleMaterial.Icon.gmd_toll))
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    selectItem(drawerItem.identifier.toInt())
                    true
                }
                .build()
        if (savedInstanceState == null) {
            selectItem(MENU_ORBIT_CALC)
        } else {
            try {
                val string = savedInstanceState.getString(DRAWER_SELECTION)
                if (string != null) {
                    drawer?.setSelection(java.lang.Long.parseLong(string))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun selectItem(position: Int, addToBack: Boolean = false) {
        var fragment: Fragment? = null
        when (position) {
            MENU_ORBIT_CALC -> {
                fragment = OrbitCalcFragment()
                toolbar?.title = getString(R.string.calc_orbit_fragment)
            }
            MENU_ASTEROIDS -> {
                fragment = AsteroidsFragment()
                toolbar?.title = getString(R.string.fragment_asteroids)
            }
            MENU_PLANETS -> {
                fragment = PlanetsFragment.newInstance()
                toolbar?.title = getString(R.string.fragment_asteroids)
            }
        }
        if (fragment != null) {
            replaceFragmentInActivity(fragment, R.id.container, addToBack)
            drawer?.closeDrawer()
        }
    }

    fun startFragmentWithTargetFragment(target: Fragment,requestFragment: Fragment, requestCode: Int) {
        requestFragment.setTargetFragment(target, requestCode)
        replaceFragmentInActivity(requestFragment, R.id.container, true)
    }

    companion object {
        private val MENU_ORBIT_CALC = 1
        private val MENU_ASTEROIDS = 2
        private val MENU_PLANETS = 3
        private val DRAWER_SELECTION = "drawer_selection"
    }
}
